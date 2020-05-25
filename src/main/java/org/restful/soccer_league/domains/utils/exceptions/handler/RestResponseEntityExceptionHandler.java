package org.restful.soccer_league.domains.utils.exceptions.handler;

import com.github.fge.jsonpatch.JsonPatchException;
import org.restful.soccer_league.domains.utils.RequestURIUtils;
import org.restful.soccer_league.domains.utils.exceptions.ConflictException;
import org.restful.soccer_league.domains.utils.exceptions.ForbiddenException;
import org.restful.soccer_league.domains.utils.exceptions.ResourceNotFoundException;
import org.restful.soccer_league.domains.utils.exceptions.UnknownFieldException;
import org.restful.soccer_league.domains.utils.exceptions.enums.ErrorCodeEnum;
import org.restful.soccer_league.domains.utils.exceptions.handler.pojo.ClientResponse;
import org.restful.soccer_league.domains.utils.exceptions.handler.pojo.DetailError;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler({ConflictException.class})
    public ResponseEntity<Object> handleConflict(ConflictException ex) {
        ClientResponse clientResponse = new ClientResponse(null,
                new DetailError(ex.getResource(), ex.getField(), ex.getMessage(), ErrorCodeEnum.ALREADY_EXISTS.getCode()));

        return new ResponseEntity<>(clientResponse, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String resource = RequestURIUtils.getResourceFromURI(request.getRequestURI());

        List<DetailError> details = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            details.add(new DetailError(StringUtils.capitalize(resource), error.getField(), error.getDefaultMessage(), ErrorCodeEnum.MISSING_FIELD.getCode()));
        }

        ClientResponse clientResponse = new ClientResponse(null, details);

        return new ResponseEntity<>(clientResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {
        ClientResponse clientResponse = new ClientResponse(null,
                new DetailError(ex.getResource(), null, ex.getMessage(), ErrorCodeEnum.MISSING.getCode()));

        return new ResponseEntity<>(clientResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<Object> handleForbiddenException(ForbiddenException ex) {
        ClientResponse clientResponse = new ClientResponse(null,
                new DetailError(ex.getResource(), null, ex.getMessage(), ErrorCodeEnum.FORBIDDEN.getCode()));

        return new ResponseEntity<>(clientResponse, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({UnsatisfiedServletRequestParameterException.class})
    public ResponseEntity<Object> handleUnsatisfiedServletRequestParameterException(UnsatisfiedServletRequestParameterException ex, HttpServletRequest request) {
        String resource = RequestURIUtils.getResourceFromURI(request.getRequestURI());

        ClientResponse clientResponse = new ClientResponse(null,
                new DetailError(StringUtils.capitalize(resource), null, ex.getMessage(), ErrorCodeEnum.MISSING_PARAM.getCode()));

        return new ResponseEntity<>(clientResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({PropertyReferenceException.class, IllegalArgumentException.class, JsonPatchException.class, HttpMessageNotReadableException.class, UnknownFieldException.class})
    public ResponseEntity<Object> handlePropertyReferenceException(Exception ex, HttpServletRequest request) {
        String resource = RequestURIUtils.getResourceFromURI(request.getRequestURI());

        ClientResponse clientResponse = new ClientResponse(null,
                new DetailError(StringUtils.capitalize(resource), null, ex.getMessage(), ErrorCodeEnum.INVALID.getCode()));

        return new ResponseEntity<>(clientResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<Object> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
        String resource = RequestURIUtils.getResourceFromURI(request.getRequestURI());

        ClientResponse clientResponse = new ClientResponse(null,
                new DetailError(StringUtils.capitalize(resource), null, ex.getMessage(), ErrorCodeEnum.INVALID.getCode()));

        return new ResponseEntity<>(clientResponse, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        String resource = RequestURIUtils.getResourceFromURI(request.getRequestURI());

        ClientResponse clientResponse = new ClientResponse(null,
                new DetailError(StringUtils.capitalize(resource), null, ex.getMessage(), ErrorCodeEnum.INVALID.getCode()));

        return new ResponseEntity<>(clientResponse, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({RSQLParserException.class})
    public ResponseEntity<Object> handleRSQLParserException(RSQLParserException ex, HttpServletRequest request) {
        String resource = RequestURIUtils.getResourceFromURI(request.getRequestURI());

        ClientResponse clientResponse = new ClientResponse(null,
                new DetailError(StringUtils.capitalize(resource), null, "Have invalid params in your search.", ErrorCodeEnum.INVALID.getCode()));

        return new ResponseEntity<>(clientResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidDataAccessResourceUsageException.class})
    public ResponseEntity<Object> handleInvalidDataAccessResourceUsageException(HttpServletRequest request) {
        String resource = RequestURIUtils.getResourceFromURI(request.getRequestURI());

        ClientResponse clientResponse = new ClientResponse(null,
                new DetailError(StringUtils.capitalize(resource), request.getParameter("sort"),
                        "Don't use nested object in sort mechanism with search.", ErrorCodeEnum.INVALID.getCode()));

        return new ResponseEntity<>(clientResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidDataAccessApiUsageException.class})
    public ResponseEntity<Object> handleInvalidDataAccessApiUsageException(HttpServletRequest request) {
        String resource = RequestURIUtils.getResourceFromURI(request.getRequestURI());

        ClientResponse clientResponse = new ClientResponse(null,
                new DetailError(StringUtils.capitalize(resource), request.getParameter("search"),
                        "Have invalid attributes in your search.", ErrorCodeEnum.INVALID.getCode()));

        return new ResponseEntity<>(clientResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidComparisonOperationException.class})
    public ResponseEntity<Object> handleInvalidComparisonOperationException(InvalidComparisonOperationException ex) {
        ClientResponse clientResponse = new ClientResponse(null,
                new DetailError(ex.getResource(), ex.getComparisonOperation(),
                        ex.getMessage(), ErrorCodeEnum.INVALID.getCode()));

        return new ResponseEntity<>(clientResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}

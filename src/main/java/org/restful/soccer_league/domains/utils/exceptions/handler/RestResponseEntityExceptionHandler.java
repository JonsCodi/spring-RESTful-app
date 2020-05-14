package org.restful.soccer_league.domains.utils.exceptions.handler;

import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.utils.exceptions.ConflictException;
import org.restful.soccer_league.domains.utils.exceptions.ForbiddenException;
import org.restful.soccer_league.domains.utils.exceptions.ResourceNotFoundException;
import org.restful.soccer_league.domains.utils.exceptions.enums.ErrorCodeEnum;
import org.restful.soccer_league.domains.utils.exceptions.handler.pojo.ClientResponse;
import org.restful.soccer_league.domains.utils.exceptions.handler.pojo.DetailError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    private static final String ERROR = "error";
    private String requestURI;

    @ExceptionHandler({ConflictException.class})
    public ResponseEntity<Object> handleConflict(ConflictException ex) {
        ClientResponse clientResponse = new ClientResponse(null,
                new DetailError(ex.getResource(), ex.getField(), ex.getMessage(), ErrorCodeEnum.ALREADY_EXISTS.getCode()), ERROR);

        return new ResponseEntity<>(clientResponse, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        requestURI = request.getRequestURI();
        String resource = requestURI.substring(request.getRequestURI().lastIndexOf("/") + 1);

        List<DetailError> details = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            details.add(new DetailError(StringUtils.capitalize(resource), error.getField(), error.getDefaultMessage(), ErrorCodeEnum.MISSING_FIELD.getCode()));
        }

        ClientResponse clientResponse = new ClientResponse(null, details, ERROR);

        return new ResponseEntity<>(clientResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {
        ClientResponse clientResponse = new ClientResponse(null,
                new DetailError(ex.getResource(), null, ex.getMessage(), ErrorCodeEnum.MISSING.getCode()), ERROR);

        return new ResponseEntity<>(clientResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<Object> handleForbiddenException(ForbiddenException ex) {
        ClientResponse clientResponse = new ClientResponse(null,
                new DetailError(ex.getResource(), null, ex.getMessage(), ErrorCodeEnum.FORBIDDEN.getCode()), ERROR);

        return new ResponseEntity<>(clientResponse, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        requestURI = request.getRequestURI();
        String resource = requestURI.substring(request.getRequestURI().lastIndexOf("/") + 1);

        ClientResponse clientResponse = new ClientResponse(null,
                new DetailError(StringUtils.capitalize(resource), null, ex.getMessage(), ErrorCodeEnum.INVALID.getCode()), ERROR);

        return new ResponseEntity<>(clientResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({JsonPatchException.class})
    public ResponseEntity<Object> handleJsonPatchException(JsonPatchException ex, HttpServletRequest request) {
        requestURI = request.getRequestURI();
        String resource = requestURI.substring(request.getRequestURI().lastIndexOf("/") + 1);

        ClientResponse clientResponse = new ClientResponse(null,
                new DetailError(StringUtils.capitalize(resource), null, ex.getMessage(), ErrorCodeEnum.INVALID.getCode()), ERROR);

        return new ResponseEntity<>(clientResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


}

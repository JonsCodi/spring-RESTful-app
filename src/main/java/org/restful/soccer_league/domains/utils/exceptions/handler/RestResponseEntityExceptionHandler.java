package org.restful.soccer_league.domains.utils.exceptions.handler;

import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.utils.exceptions.ConflictException;
import org.restful.soccer_league.domains.utils.exceptions.ForbiddenException;
import org.restful.soccer_league.domains.utils.exceptions.ResourceNotFoundException;
import org.restful.soccer_league.domains.utils.exceptions.enums.ErrorCodeEnum;
import org.restful.soccer_league.domains.utils.exceptions.handler.pojo.ApiError;
import org.restful.soccer_league.domains.utils.exceptions.handler.pojo.DetailError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    private static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";
    private static final String ERROR = "error";

    @ExceptionHandler({ConflictException.class})
    public ResponseEntity<Object> handleConflict(ConflictException ex) {
        ApiError apiError = new ApiError(System.currentTimeMillis(), LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMATTER)),
                ERROR, null, new DetailError(ex.getField(), ex.getMessage(), ErrorCodeEnum.ALREADY_EXISTS.getCode()));

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<DetailError> details = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            details.add(new DetailError(error.getField(), error.getDefaultMessage(), ErrorCodeEnum.MISSING_FIELD.getCode()));
        }

        ApiError apiError = new ApiError(System.currentTimeMillis(), LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMATTER)),
               ERROR, null, details);

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {
        ApiError apiError = new ApiError(System.currentTimeMillis(), LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMATTER)),
                ERROR, null, new DetailError(null, ex.getMessage(), ErrorCodeEnum.MISSING.getCode()));

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<Object> handleForbiddenException(ForbiddenException ex) {
        ApiError apiError = new ApiError(System.currentTimeMillis(), LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMATTER)),
                ERROR, null, new DetailError(null, ex.getMessage(), ErrorCodeEnum.FORBIDDEN.getCode()));

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ApiError apiError = new ApiError(System.currentTimeMillis(), LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMATTER)),
                ERROR, null, new DetailError(null, ex.getMessage(), ErrorCodeEnum.INVALID.getCode()));

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}

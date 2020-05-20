package org.restful.soccer_league.domains.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnknownFieldException extends RuntimeException {

    private Object fieldName;

    public UnknownFieldException(String message, Object fieldName) {
        super(message);
        this.fieldName = fieldName;
    }

}

package org.restful.soccer_league.domains.utils.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends BusinessException {

    @Getter
    public String resource;

    @Getter
    public String field;

    public ConflictException(String message, String field, String resource) {
        super(message);
        this.field = field;
        this.resource = resource;
    }

}

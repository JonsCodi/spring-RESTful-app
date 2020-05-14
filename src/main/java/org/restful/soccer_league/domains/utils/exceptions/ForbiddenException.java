package org.restful.soccer_league.domains.utils.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends BusinessException {

    @Getter
    public String resource;

    public ForbiddenException(String message, String resource) {
        super(message);
        this.resource = resource;
    }
}
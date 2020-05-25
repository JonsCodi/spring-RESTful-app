package org.restful.soccer_league.domains.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException(String message, String resource) {
        super(message, resource);
    }

}

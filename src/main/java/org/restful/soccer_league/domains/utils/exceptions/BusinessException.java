package org.restful.soccer_league.domains.utils.exceptions;

import lombok.Getter;

public class BusinessException extends RuntimeException {

    @Getter
    public String resource;

    public BusinessException(String message, String resource) {
        super(message);
        this.resource = resource;
    }

}

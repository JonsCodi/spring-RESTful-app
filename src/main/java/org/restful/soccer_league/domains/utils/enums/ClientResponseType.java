package org.restful.soccer_league.domains.utils.enums;

import lombok.Getter;

public enum ClientResponseType {

    SUCCESS("SUCCESS"), ERROR("ERROR");

    @Getter
    private String message;

    ClientResponseType(String message) {
        this.message = message;
    }

}

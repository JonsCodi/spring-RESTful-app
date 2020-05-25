package org.restful.soccer_league.domains.utils.exceptions.enums;

import lombok.Getter;

public enum ErrorCodeEnum {

    MISSING("missing"),
    MISSING_FIELD("missing_field"),
    MISSING_PARAM("missing_param"),
    INVALID("invalid"),
    INVALID_PARAM("invalid_param"),
    FORBIDDEN("forbidden"),
    ALREADY_EXISTS("already_exists"),


    ;

    @Getter
    private String code;

    ErrorCodeEnum(String code) {
        this.code = code;
    }
}

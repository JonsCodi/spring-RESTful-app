package org.restful.soccer_league.domains.utils.exceptions.enums;

public enum ErrorCodeEnum {

    MISSING("missing"),
    MISSING_FIELD("missing_field"),
    INVALID("invalid"),
    FORBIDDEN("forbidden"),
    ALREADY_EXISTS("already_exists");

    private String code;

    ErrorCodeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

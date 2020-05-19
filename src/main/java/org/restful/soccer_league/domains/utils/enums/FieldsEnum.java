package org.restful.soccer_league.domains.utils.enums;

import lombok.Getter;

public enum FieldsEnum {

    ALL("all");

    @Getter
    private String field;

    FieldsEnum(String field) {
        this.field = field;
    }
}

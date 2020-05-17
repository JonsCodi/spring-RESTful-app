package org.restful.soccer_league.domains.utils.response;

import lombok.Getter;

public enum LinkEnum {

    FIRST("first"), PREV("prev"), NEXT("next"), LAST("last");

    @Getter
    private String rel;

    LinkEnum(String rel) {
        this.rel = rel;
    }

}

package org.restful.soccer_league.domains.utils.enums;

import lombok.Getter;

public enum FiltersEnum {

    TEAM("team_filter"),
    PLAYER("player_filter"),
    COACH("coach_filter"),
    ;

    @Getter
    private String filter;

    FiltersEnum(String filter) {
        this.filter = filter;
    }
}

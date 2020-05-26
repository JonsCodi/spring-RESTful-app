package org.restful.soccer_league.domains.team.api.v1.web.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Getter;
import lombok.Setter;
import org.restful.soccer_league.domains.team.enums.PersonType;

@Setter
@Getter
@JsonFilter("player_filter")
public class PlayerModel extends PersonModel {

    public int number;
    public String position;
    private boolean isCaptain;

    public PlayerModel() {
        setPersonType(PersonType.PLAYER);
    }
}

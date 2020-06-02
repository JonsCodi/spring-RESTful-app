package org.restful.soccer_league.domains.team.api.v1.web.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Getter;
import lombok.Setter;
import org.restful.soccer_league.domains.team.enums.PersonTypeEnum;

@Setter
@JsonFilter("coach_filter")
@Getter
public class CoachModel extends PersonModel {

    private String accrLevel;
    private int experience;

    public CoachModel() {
        setPersonType(PersonTypeEnum.COACH);
    }

}

package org.restful.soccer_league.domains.team.api.v1.web.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.restful.soccer_league.domains.team.enums.AccrLevel;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CoachCreateRequest extends BasePersonRequest {

    @NotNull
    private AccrLevel accrLevel;

    @NotNull
    private int experience;

}

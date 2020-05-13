package org.restful.soccer_league.domains.team.api.v1.web.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.restful.soccer_league.domains.team.enums.Position;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlayerCreateRequest extends BasePersonRequest {

    @NotNull
    public int number;

    @NotNull
    public Position position;

    @NotNull
    public boolean isCaptain;

}

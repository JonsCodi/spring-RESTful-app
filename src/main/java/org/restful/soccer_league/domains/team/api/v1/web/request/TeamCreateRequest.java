package org.restful.soccer_league.domains.team.api.v1.web.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamCreateRequest implements Serializable {

    @NotBlank
    private String name;

}

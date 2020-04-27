package org.restful.soccer_league.domains.team.api.v1.web.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamUpdateRequest implements Serializable {

    private String name;
    private RecordRequest record;

}

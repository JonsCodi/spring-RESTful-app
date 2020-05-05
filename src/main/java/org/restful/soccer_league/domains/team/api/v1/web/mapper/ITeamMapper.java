package org.restful.soccer_league.domains.team.api.v1.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.restful.soccer_league.domains.team.api.v1.web.request.TeamUpdateRequest;
import org.restful.soccer_league.domains.team.entity.Team;

@Mapper
public interface ITeamMapper {

    TeamUpdateRequest asRequest(Team team);

    void update(@MappingTarget Team team, TeamUpdateRequest teamUpdateRequest);

}

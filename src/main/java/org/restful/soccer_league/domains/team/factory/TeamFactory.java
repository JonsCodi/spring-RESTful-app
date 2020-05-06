package org.restful.soccer_league.domains.team.factory;

import org.restful.soccer_league.domains.team.api.v1.web.request.TeamCreateRequest;
import org.restful.soccer_league.domains.team.entity.Team;

public final class TeamFactory {

    public static Team createTeam(TeamCreateRequest teamCreateRequest) {
        Team team = new Team(teamCreateRequest.getName());
        team.setName(teamCreateRequest.getName());

        return team;
    }
}

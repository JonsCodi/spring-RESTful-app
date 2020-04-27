package org.restful.soccer_league.domains.team.factory;

import org.restful.soccer_league.domains.team.api.v1.web.request.TeamCreateRequest;
import org.restful.soccer_league.domains.team.entity.Record;
import org.restful.soccer_league.domains.team.entity.Team;

public class TeamFactory {

    public static Team createTeam(TeamCreateRequest teamCreateRequest) {
        Team team = new Team();
        Record record = new Record(0, 0);
        team.setName(teamCreateRequest.getName());
        team.setRecord(record);

        return team;
    }
}

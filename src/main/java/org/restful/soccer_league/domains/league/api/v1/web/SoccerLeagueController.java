package org.restful.soccer_league.domains.league.api.v1.web;

import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.league.api.v1.web.request.SoccerLeagueRequest;
import org.restful.soccer_league.domains.league.entity.SoccerLeague;
import org.restful.soccer_league.domains.league.service.ISoccerLeagueService;
import org.restful.soccer_league.domains.team.entity.Team;
import org.restful.soccer_league.domains.team.service.ITeamService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/soccer-leagues")
public class SoccerLeagueController {

    private final ISoccerLeagueService soccerLeagueService;
    private final ITeamService teamService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SoccerLeague> create(@RequestBody SoccerLeagueRequest soccerLeagueRequest) {
        Set<Team> teams = soccerLeagueRequest.getTeams().stream()
                .map(teamService::findByName)
                .collect(Collectors.toSet());

        SoccerLeague soccerLeague = SoccerLeague.builder()
                .name(soccerLeagueRequest.getName())
                .teams(teams)
                .build();

        soccerLeague = soccerLeagueService.create(soccerLeague);

        teamService.addTeamsIntoLeague(teams, soccerLeague);

        return ResponseEntity.ok(soccerLeague);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SoccerLeague>> get() {
        return ResponseEntity.ok(soccerLeagueService.findAll());
    }

}

package org.restful.soccer_league.domains.league.api.v1.web;

import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.league.api.v1.web.request.SoccerLeagueRequest;
import org.restful.soccer_league.domains.league.entity.Game;
import org.restful.soccer_league.domains.league.entity.SoccerLeague;
import org.restful.soccer_league.domains.league.factory.GameFactory;
import org.restful.soccer_league.domains.league.service.ISoccerLeagueService;
import org.restful.soccer_league.domains.team.entity.Team;
import org.restful.soccer_league.domains.team.service.ITeamService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
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
        SoccerLeague soccerLeague = createSoccerLeagueObject(soccerLeagueRequest);

        soccerLeague = soccerLeagueService.create(soccerLeague);
        if(Objects.nonNull(soccerLeague.getTeams()) && !soccerLeague.getTeams().isEmpty()) {
            soccerLeague.getTeams().forEach(teamService::update);
        }

        return ResponseEntity.ok(soccerLeague);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SoccerLeague>> getAll() {
        return ResponseEntity.ok(soccerLeagueService.findAll());
    }

    @GetMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SoccerLeague> get(@PathVariable("name") String name) {
        return ResponseEntity.ok(soccerLeagueService.findByName(name));
    }

    @PostMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SoccerLeague> update(@PathVariable("name") String name, @RequestBody SoccerLeagueRequest soccerLeagueRequest) {
        SoccerLeague soccerLeague = soccerLeagueService.findByName(name);

        SoccerLeague soccerLeagueForUpdate = createSoccerLeagueObject(soccerLeagueRequest);

        soccerLeagueForUpdate.setId(soccerLeague.getId());

        return ResponseEntity.ok(soccerLeagueService.update(soccerLeagueForUpdate));
    }

    @PostMapping(path = "/{name}")
    public ResponseEntity delete(@PathVariable("name") String name) {
        SoccerLeague soccerLeague = soccerLeagueService.findByName(name);

        soccerLeagueService.delete(soccerLeague);

        return ResponseEntity.ok().build();
    }

    private SoccerLeague createSoccerLeagueObject(SoccerLeagueRequest soccerLeagueRequest) {
        SoccerLeague soccerLeague = SoccerLeague.builder()
                .name(soccerLeagueRequest.getName())
                .build();

        setTeamsIfExist(soccerLeagueRequest, soccerLeague);
        setGamesIfExist(soccerLeagueRequest, soccerLeague);

        return soccerLeague;
    }

    private void setGamesIfExist(final SoccerLeagueRequest soccerLeagueRequest, SoccerLeague soccerLeague) {
        if (Objects.nonNull(soccerLeagueRequest.getGames()) && !soccerLeagueRequest.getGames().isEmpty()) {
            Set<Game> games = soccerLeagueRequest.getGames().stream()
                    .map(GameFactory::create)
                    .collect(Collectors.toSet());

            soccerLeague.setGames(games);
        }
    }

    private void setTeamsIfExist(final SoccerLeagueRequest soccerLeagueRequest, SoccerLeague soccerLeague) {
        if (Objects.nonNull(soccerLeagueRequest.getTeams()) && !soccerLeagueRequest.getTeams().isEmpty()) {
            Set<Team> teams = soccerLeagueRequest.getTeams().stream()
                    .map(team -> {
                        Team teamFromRepository = teamService.findByName(team);

                        teamFromRepository.getSoccerLeagues().add(soccerLeague);

                        return teamFromRepository;
                    })
                    .collect(Collectors.toSet());

            soccerLeague.setTeams(teams);
        }
    }

}

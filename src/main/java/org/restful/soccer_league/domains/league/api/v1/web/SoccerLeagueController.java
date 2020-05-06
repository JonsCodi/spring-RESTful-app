package org.restful.soccer_league.domains.league.api.v1.web;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.league.api.v1.web.request.SoccerLeagueRequest;
import org.restful.soccer_league.domains.league.entity.Game;
import org.restful.soccer_league.domains.league.entity.SoccerLeague;
import org.restful.soccer_league.domains.league.factory.GameFactory;
import org.restful.soccer_league.domains.league.service.ISoccerLeagueService;
import org.restful.soccer_league.domains.team.entity.Team;
import org.restful.soccer_league.domains.utils.components.PatchHelperComponent;
import org.restful.soccer_league.domains.utils.constants.PatchMediaType;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/soccer-leagues")
public class SoccerLeagueController {

    private final ISoccerLeagueService soccerLeagueService;
    private final PatchHelperComponent patchHelperComponent;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SoccerLeague> create(@RequestBody SoccerLeagueRequest soccerLeagueRequest) {
        SoccerLeague soccerLeague = soccerLeagueService.create(createSoccerLeagueObject(soccerLeagueRequest));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(soccerLeague.getId())
                .toUri();

        return ResponseEntity.created(location)
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SoccerLeague>> getAll() {
        return ResponseEntity.ok(soccerLeagueService.findAll());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SoccerLeague> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(soccerLeagueService.findById(id));
    }

    @PatchMapping(path = "/{id}", consumes = PatchMediaType.APPLICATION_JSON_PATCH_VALUE)
    public ResponseEntity<SoccerLeague> update(@PathVariable("id") Long id, @RequestBody JsonPatch jsonPatch) {
        SoccerLeague soccerLeague = soccerLeagueService.findById(id);

        SoccerLeague soccerLeaguePatched = patchHelperComponent.applyPatch(jsonPatch, soccerLeague);

        soccerLeagueService.update(soccerLeaguePatched);

        return ResponseEntity.noContent()
                .build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        soccerLeagueService.deleteById(id);

        return ResponseEntity.ok().build();
    }

    private SoccerLeague createSoccerLeagueObject(SoccerLeagueRequest soccerLeagueRequest) {
        SoccerLeague soccerLeague = new SoccerLeague(soccerLeagueRequest.getName());

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
                    .map(Team::new)
                    .collect(Collectors.toSet());

            soccerLeague.setTeams(teams);
        }
    }

}

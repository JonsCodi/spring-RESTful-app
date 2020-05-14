package org.restful.soccer_league.domains.league.api.v1.web;

import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.league.entity.Game;
import org.restful.soccer_league.domains.league.service.ISoccerLeagueService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/soccer-leagues/{id}/games")
public class GameController {

    private final ISoccerLeagueService soccerLeagueService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Game>> getAll(@PathVariable Long id) {
        return ResponseEntity.ok(soccerLeagueService.findById(id).getGames());
    }

}

package org.restful.soccer_league.domains.league.api.v1.web;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/soccer-leagues/{id}/games")
public class SoccerLeagueGameController {


}

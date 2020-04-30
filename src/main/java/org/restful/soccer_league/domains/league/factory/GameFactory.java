package org.restful.soccer_league.domains.league.factory;

import org.restful.soccer_league.domains.league.api.v1.web.request.GameRequest;
import org.restful.soccer_league.domains.league.entity.Game;

public final class GameFactory {

    public static Game create(GameRequest gameRequest) {
        return Game.builder()
                .location(gameRequest.getLocation())
                .teamA(gameRequest.getTeamA())
                .teamB(gameRequest.getTeamB())
                .scoreTeamA(gameRequest.getScoreTeamA())
                .scoreTeamB(gameRequest.getScoreTeamB())
                .build();
    }
}

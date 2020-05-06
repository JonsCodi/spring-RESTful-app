package org.restful.soccer_league.domains.league.factory;

import org.restful.soccer_league.domains.league.api.v1.web.request.GameRequest;
import org.restful.soccer_league.domains.league.entity.Game;

public final class GameFactory {

    public static Game create(GameRequest gameRequest) {
        return new Game(gameRequest.getLocation(), gameRequest.getTeamA(), gameRequest.getTeamB());
    }
}

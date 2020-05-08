package org.restful.soccer_league.domains.league.factory;

import org.restful.soccer_league.domains.league.api.v1.web.request.SoccerLeagueRequest;
import org.restful.soccer_league.domains.league.entity.Game;
import org.restful.soccer_league.domains.league.entity.SoccerLeague;
import org.restful.soccer_league.domains.team.entity.Team;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class SoccerLeagueFactory {

    public static SoccerLeague createSoccerLeagueObject(SoccerLeagueRequest soccerLeagueRequest) {
        SoccerLeague soccerLeague = new SoccerLeague(soccerLeagueRequest.getName());

        setTeamsIfExist(soccerLeagueRequest, soccerLeague);
        setGamesIfExist(soccerLeagueRequest, soccerLeague);

        return soccerLeague;
    }

    private static void setTeamsIfExist(final SoccerLeagueRequest soccerLeagueRequest, SoccerLeague soccerLeague) {
        if (Objects.nonNull(soccerLeagueRequest.getTeams()) && !soccerLeagueRequest.getTeams().isEmpty()) {
            Set<Team> teams = soccerLeagueRequest.getTeams().stream()
                    .map(Team::new)
                    .collect(Collectors.toSet());

            soccerLeague.setTeams(teams);
        }
    }

    private static void setGamesIfExist(final SoccerLeagueRequest soccerLeagueRequest, SoccerLeague soccerLeague) {
        if (Objects.nonNull(soccerLeagueRequest.getGames()) && !soccerLeagueRequest.getGames().isEmpty()) {
            Set<Game> games = soccerLeagueRequest.getGames().stream()
                    .map(GameFactory::create)
                    .collect(Collectors.toSet());

            soccerLeague.setGames(games);
        }
    }

}

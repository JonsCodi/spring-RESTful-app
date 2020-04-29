package org.restful.soccer_league.domains.league.service;

import org.restful.soccer_league.domains.league.entity.SoccerLeague;

public interface ISoccerLeagueService {

    SoccerLeague create(SoccerLeague soccerLeague);
    SoccerLeague update(SoccerLeague soccerLeague);
    void delete(SoccerLeague soccerLeague);
    void deleteById(Long id);



}

package org.restful.soccer_league.domains.league.service.impl;

import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.league.entity.SoccerLeague;
import org.restful.soccer_league.domains.league.repository.ISoccerRepository;
import org.restful.soccer_league.domains.league.service.ISoccerLeagueService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SoccerLeagueServiceImpl implements ISoccerLeagueService {

    private final ISoccerRepository soccerRepository;

    @Override
    public void create(SoccerLeague soccerLeague) {

    }

    @Override
    public void update(SoccerLeague soccerLeague) {

    }

    @Override
    public void delete(SoccerLeague soccerLeague) {

    }

    @Override
    public void deleteById(Long id) {

    }
}

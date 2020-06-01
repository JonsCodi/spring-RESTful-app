package org.restful.soccer_league.domains.league.service;

import org.restful.soccer_league.domains.league.entity.SoccerLeague;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface ISoccerLeagueService {

    SoccerLeague create(SoccerLeague soccerLeague);
    void update(SoccerLeague soccerLeague);
    void deleteById(Long id);

    Page<SoccerLeague> findAll(Pageable pageable);
    Page<SoccerLeague> searchBySpecification(Specification<SoccerLeague> spec, Pageable pageable);

    SoccerLeague findById(Long id);
}

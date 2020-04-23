package org.restful.soccer_league.domains.league.repository;

import org.restful.soccer_league.domains.league.entity.SoccerLeague;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ISoccerRepository extends PagingAndSortingRepository<SoccerLeague, Long> {

    Optional<SoccerLeague> findByName(String name);

}

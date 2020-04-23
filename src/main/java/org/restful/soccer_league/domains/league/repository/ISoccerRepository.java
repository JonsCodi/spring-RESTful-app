package org.restful.soccer_league.domains.league.repository;

import org.restful.soccer_league.domains.league.entity.SoccerLeague;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ISoccerRepository extends PagingAndSortingRepository<SoccerLeague, Long> {
}

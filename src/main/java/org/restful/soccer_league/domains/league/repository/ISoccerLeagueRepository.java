package org.restful.soccer_league.domains.league.repository;

import org.restful.soccer_league.domains.league.entity.SoccerLeague;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ISoccerLeagueRepository extends PagingAndSortingRepository<SoccerLeague, Long>, JpaSpecificationExecutor<SoccerLeague> {

    Optional<SoccerLeague> findByName(String name);

}

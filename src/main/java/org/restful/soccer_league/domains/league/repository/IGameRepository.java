package org.restful.soccer_league.domains.league.repository;

import org.restful.soccer_league.domains.league.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IGameRepository extends PagingAndSortingRepository<Game, Long>, JpaSpecificationExecutor<Game> {

    Page<Game> findAllBySoccerLeagueId(Long idSoccerLeague, Pageable pageable);

}

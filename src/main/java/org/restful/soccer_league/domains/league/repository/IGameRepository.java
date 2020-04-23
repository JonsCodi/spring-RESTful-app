package org.restful.soccer_league.domains.league.repository;

import org.restful.soccer_league.domains.league.entity.Game;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IGameRepository extends PagingAndSortingRepository<Game, Long> {

}

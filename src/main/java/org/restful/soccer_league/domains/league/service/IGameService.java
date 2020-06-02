package org.restful.soccer_league.domains.league.service;

import org.restful.soccer_league.domains.league.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface IGameService {

    Page<Game> searchBySpecification(Specification<Game> spec, Pageable pageable);

}

package org.restful.soccer_league.domains.league.service.impl;

import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.league.entity.Game;
import org.restful.soccer_league.domains.league.repository.IGameRepository;
import org.restful.soccer_league.domains.league.service.IGameService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GameServiceImpl implements IGameService {

    private final IGameRepository gameRepository;

    @Override
    public Page<Game> searchBySpecification(Specification<Game> spec, Pageable pageable) {
        return gameRepository.findAll(spec, pageable);
    }

}

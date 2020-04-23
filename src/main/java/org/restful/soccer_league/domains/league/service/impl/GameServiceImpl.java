package org.restful.soccer_league.domains.league.service.impl;

import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.league.entity.Game;
import org.restful.soccer_league.domains.league.repository.IGameRepository;
import org.restful.soccer_league.domains.league.service.IGameService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements IGameService {

    private final IGameRepository gameRepository;

    @Override
    public void create(Game game) {

    }

    @Override
    public void update(Game game) {

    }

    @Override
    public void delete(Game game) {

    }

    @Override
    public void deleteById(Long id) {

    }
}

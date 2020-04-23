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
        if(game.getTeamA().equals(game.getTeamB())){
            throw new RuntimeException("Cannot create a game with same Teams name!!!");
        }

        gameRepository.save(game);
    }

    @Override
    public void update(Game game) {
        gameRepository.save(game);
    }

    @Override
    public void delete(Game game) {
        gameRepository.delete(game);
    }

    @Override
    public void deleteById(Long id) {
        gameRepository.deleteById(id);
    }

}

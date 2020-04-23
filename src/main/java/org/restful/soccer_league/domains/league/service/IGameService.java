package org.restful.soccer_league.domains.league.service;

import org.restful.soccer_league.domains.league.entity.Game;

public interface IGameService {

    void create(Game game);
    void update(Game game);
    void delete(Game game);
    void deleteById(Long id);

}

package org.restful.soccer_league.domains.team.service;

import org.restful.soccer_league.domains.team.entity.Player;
import org.restful.soccer_league.domains.team.enums.Position;

import java.util.List;
import java.util.Optional;

public interface IPlayerService extends IPersonService<Player> {

    Optional<Player> findByNumber(int number);
    Optional<List<Player>> findByPosition(Position position);

}

package org.restful.soccer_league.domains.team.repository;

import org.restful.soccer_league.domains.team.entity.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends PersonBaseRepository<Player> {

    Optional<Player> findByNumber(int number);

    Optional<List<Player>> findByPosition(String position);

}

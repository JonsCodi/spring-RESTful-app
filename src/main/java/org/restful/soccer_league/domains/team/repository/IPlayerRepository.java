package org.restful.soccer_league.domains.team.repository;

import org.restful.soccer_league.domains.team.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IPlayerRepository extends IPersonBaseRepository<Player> {

    Page<Player> findAllByTeamId(Long idTeam, Pageable pageable);
    Optional<Player> findByIdAndTeamId(Long id, Long idTeam);

}

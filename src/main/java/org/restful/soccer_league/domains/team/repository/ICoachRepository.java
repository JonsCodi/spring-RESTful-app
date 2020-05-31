package org.restful.soccer_league.domains.team.repository;

import org.restful.soccer_league.domains.team.entity.Coach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICoachRepository extends IPersonBaseRepository<Coach> {

    Page<Coach> findAllByTeamId(Long idTeam, Pageable pageable);
    Optional<Coach> findByIdAndTeamId(Long id, Long idTeam);

}

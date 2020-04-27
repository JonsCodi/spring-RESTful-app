package org.restful.soccer_league.domains.team.repository;

import org.restful.soccer_league.domains.team.entity.Team;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ITeamRepository extends PagingAndSortingRepository<Team, Long> {

    Optional<Team> findByName(String name);
}

package org.restful.soccer_league.domains.team.repository;

import org.restful.soccer_league.domains.team.entity.Coach;

import java.util.List;
import java.util.Optional;

public interface ICoachRepository extends IPersonBaseRepository<Coach> {

    Optional<List<Coach>> findByAccrLevel(String accrLevel);

    Optional<List<Coach>> findByExperience(int experience);

}

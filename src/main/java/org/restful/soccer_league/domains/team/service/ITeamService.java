package org.restful.soccer_league.domains.team.service;

import org.restful.soccer_league.domains.team.entity.Person;
import org.restful.soccer_league.domains.team.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface ITeamService {

    Team create(Team team);
    void update(Team team);
    void deleteById(long id);

    Team findById(Long id);

    Page<Team> findAll(Pageable pageable);
    Page<Team> searchBySpecification(Specification<Team> spec, Pageable pageable);

    void addPerson(Team team, Person person);
    void removePerson(Team team, Person person);

}

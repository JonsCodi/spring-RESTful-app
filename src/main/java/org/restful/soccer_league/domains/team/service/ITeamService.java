package org.restful.soccer_league.domains.team.service;

import org.restful.soccer_league.domains.team.entity.Person;
import org.restful.soccer_league.domains.team.entity.Team;

public interface ITeamService {

    Team create(Team team);
    Team update(Team team);
    void delete(Team team);
    void deleteById(long id);

    Team findByName(String name);

    Person addPerson(Team team, Person person);
}

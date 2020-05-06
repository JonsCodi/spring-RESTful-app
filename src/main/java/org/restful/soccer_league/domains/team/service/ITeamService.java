package org.restful.soccer_league.domains.team.service;

import org.restful.soccer_league.domains.team.entity.Person;
import org.restful.soccer_league.domains.team.entity.Team;

import java.util.List;

public interface ITeamService {

    Team create(Team team);
    void update(Team team);
    void delete(Team team);
    void deleteById(long id);

    Team findById(Long id);
    Team findByName(String name);

    List<Team> findAll();

    void addPerson(Team team, Person person);
    void removePerson(Team team, Person person);

}

package org.restful.soccer_league.domains.team.service;

import org.restful.soccer_league.domains.league.entity.SoccerLeague;
import org.restful.soccer_league.domains.team.entity.Person;
import org.restful.soccer_league.domains.team.entity.Team;

import java.util.List;
import java.util.Set;

public interface ITeamService {

    Team create(Team team);
    Team update(Team team);
    void delete(Team team);
    void deleteById(long id);

    Team findByName(String name);

    List<Team> findAll();

    Person addPerson(Team team, Person person);

    void addTeamsIntoLeague(Set<Team> teams, SoccerLeague soccerLeague);
}

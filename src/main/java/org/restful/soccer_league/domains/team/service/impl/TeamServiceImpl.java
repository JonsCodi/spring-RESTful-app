package org.restful.soccer_league.domains.team.service.impl;

import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.team.entity.Coach;
import org.restful.soccer_league.domains.team.entity.Person;
import org.restful.soccer_league.domains.team.entity.Player;
import org.restful.soccer_league.domains.team.entity.Team;
import org.restful.soccer_league.domains.team.repository.ICoachRepository;
import org.restful.soccer_league.domains.team.repository.IPlayerRepository;
import org.restful.soccer_league.domains.team.repository.ITeamRepository;
import org.restful.soccer_league.domains.team.service.ITeamService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements ITeamService {
    
    private final ITeamRepository teamRepository;
    private final IPlayerRepository playerRepository;
    private final ICoachRepository coachRepository;

    @Override
    public Team create(Team team) {
        Optional<Team> teamAlreadyExist = teamRepository.findByName(team.getName());

        if(teamAlreadyExist.isPresent()) {
            throw new RuntimeException("Duplicate Exception TODO:");
        }

        return teamRepository.save(team);
    }

    @Override
    public Team update(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public void delete(Team team) {
        teamRepository.delete(team);
    }

    @Override
    public void deleteById(long id) {
        teamRepository.deleteById(id);
    }

    @Override
    public Team findByName(String name) {
        return teamRepository.findByName(name).orElseThrow(
                () -> new RuntimeException("Not Found!!! TODO:"));
    }

    @Override
    public List<Team> findAll() {
        return (List<Team>) teamRepository.findAll();
    }

    @Override
    public Person addPerson(Team team, Person person) {
        if(person instanceof Player) {
            return addPlayer(team, (Player) person);
        }else {
            return addCoach(team, (Coach) person);
        }
    }

    private Person addCoach(Team team, Coach person) {
        Coach coach = person;
        coach.setTeam(team);
        team.setCoach(coach);

        update(team);

        return coachRepository.save(coach);
    }

    private Person addPlayer(Team team, Player person) {
        team.getPlayers().add(person);
        person.setTeam(team);

        updateCaptain(team, person);

        update(team);

        return playerRepository.save(person);
    }

    private void updateCaptain(Team team, Player person) {
        if(person.isCaptain()) {
            team.getPlayers().stream().filter(player ->
                    !player.getName().equals(person.getName()) && player.isCaptain()).findAny().ifPresent(captain -> {
                captain.setCaptain(false);

                playerRepository.save(captain);
            });

            team.setCaptain(person.getName());
        }
    }

}

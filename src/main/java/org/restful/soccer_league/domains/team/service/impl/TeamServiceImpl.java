package org.restful.soccer_league.domains.team.service.impl;

import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.team.entity.Team;
import org.restful.soccer_league.domains.team.repository.ITeamRepository;
import org.restful.soccer_league.domains.team.service.ITeamService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements ITeamService {
    
    private final ITeamRepository teamRepository;

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
}

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
import org.restful.soccer_league.domains.utils.exceptions.ConflictException;
import org.restful.soccer_league.domains.utils.exceptions.ForbiddenException;
import org.restful.soccer_league.domains.utils.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements ITeamService {

    private final ITeamRepository teamRepository;
    private final IPlayerRepository playerRepository;
    private final ICoachRepository coachRepository;

    private final static String TEAMS = "Teams";

    @Override
    public Team create(Team team) {
        Optional<Team> teamAlreadyExist = teamRepository.findByName(team.getName());

        if (teamAlreadyExist.isPresent()) {
            throw new ConflictException("Already exist a Resource with same value of this field.", "name", TEAMS);
        }

        return teamRepository.save(team);
    }

    @Override
    public void update(Team team) {
        teamRepository.save(team);
    }

    @Override
    public void deleteById(long id) {
        teamRepository.deleteById(id);
    }

    @Override
    public Team findById(Long id) {
        return teamRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found.", TEAMS)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Team> findAll(Pageable pageable) {
        Page<Team> teamPage = teamRepository.findAll(pageable);

        return teamPage;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Team> searchBySpecification(Specification<Team> spec, Pageable pageable) {
        return teamRepository.findAll(spec, pageable);
    }

    @Override
    public void addPerson(Team team, Person person) {
        if (person instanceof Player) {
            addPlayer(team, (Player) person);
        } else {
            addCoach(team, (Coach) person);
        }
    }

    private void addCoach(Team team, Coach person) {
        person.setTeam(team);
        team.setCoach(person);

        update(team);

        coachRepository.save(person);
    }

    private void addPlayer(Team team, Player person) {
        team.getPlayers().add(person);
        person.setTeam(team);

        updateCaptain(team, person);

        update(team);

        playerRepository.save(person);
    }

    private void updateCaptain(Team team, Player person) {
        if (person.isCaptain()) {
            team.getPlayers().stream().filter(player ->
                    !player.getName().equals(person.getName()) && player.isCaptain()).findAny().ifPresent(captain -> {
                captain.setCaptain(false);

                playerRepository.save(captain);
            });

            team.setCaptain(person.getName());
        }
    }

    @Override
    public void removePerson(Team team, Person person) {
        if (person instanceof Player) {
            removePlayer(person.getId(), team);
        } else {
            removeCoach(team);
        }
    }

    private void removePlayer(Long id, Team team) {
        Optional<Player> playerOptional = team.getPlayers().stream()
                .filter(playerFromDB -> playerFromDB.getId().equals(id))
                .findAny();

        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();

            team.getPlayers().remove(player);
            if (player.isCaptain()) {
                team.setCaptain(null);
            }

            update(team);

            player.setTeam(null);
            playerRepository.save(player);
        } else {
            throw new ForbiddenException("Invalid Operation.", "Teams.Persons");
        }
    }

    private void removeCoach(Team team) {
        team.getCoach().setTeam(null);
        coachRepository.save(team.getCoach());

        team.setCoach(null);
        update(team);
    }

}

package org.restful.soccer_league.domains.team.service.impl;

import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.team.entity.Player;
import org.restful.soccer_league.domains.team.repository.IPlayerRepository;
import org.restful.soccer_league.domains.team.service.IPlayerService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements IPlayerService {

    private final IPlayerRepository playerRepository;
    
    @Override
    public void create(Player person) {
        Optional<Player> personAlreadyExist = playerRepository.findByName(person.getName());

        if(personAlreadyExist.isPresent()) {
            throw new RuntimeException("Duplicate Exception TODO:");
        }


        playerRepository.save(person);
    }

    @Override
    public void update(Player person) {
        playerRepository.save(person);
    }

    @Override
    public void delete(Player person) {
        playerRepository.delete(person);
    }

    @Override
    public void deleteById(long id) {
        playerRepository.deleteById(id);
    }

    @Override
    public Player findByName(String name) {
        return playerRepository.findByName(name).orElseThrow(
                () -> new RuntimeException("Not Found!!! TODO:"));
    }
}

package org.restful.soccer_league.domains.team.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.restful.soccer_league.domains.team.entity.Player;
import org.restful.soccer_league.domains.team.enums.Position;
import org.restful.soccer_league.domains.team.repository.IPlayerRepository;
import org.restful.soccer_league.domains.team.service.IPlayerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerServiceImpl implements IPlayerService {

    private final IPlayerRepository playerRepository;

    @Override
    public Optional<Player> findByNumber(int number) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Player>> findByPosition(Position position) {
        return Optional.empty();
    }

    @Override
    public void create(Player person) {

    }

    @Override
    public void update(Player person) {

    }

    @Override
    public void delete(Player person) {

    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public Optional<Player> findByName(String name) {
        return Optional.empty();
    }
}

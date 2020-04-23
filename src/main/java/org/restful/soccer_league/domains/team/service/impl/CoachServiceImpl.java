package org.restful.soccer_league.domains.team.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.restful.soccer_league.domains.team.entity.Coach;
import org.restful.soccer_league.domains.team.repository.CoachRepository;
import org.restful.soccer_league.domains.team.service.ICoachService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CoachServiceImpl implements ICoachService {

    private final CoachRepository coachRepository;

    @Override
    public Optional<List<Coach>> findByAccrLevel(String accrLevel) {
        return Optional.empty();
    }

    @Override
    public void create(Coach person) {

    }

    @Override
    public void update(Coach person) {

    }

    @Override
    public void delete(Coach person) {

    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public Optional<Coach> findByName(String name) {
        return Optional.empty();
    }
}

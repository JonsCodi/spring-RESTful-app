package org.restful.soccer_league.domains.team.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.restful.soccer_league.domains.team.entity.Coach;
import org.restful.soccer_league.domains.team.repository.ICoachRepository;
import org.restful.soccer_league.domains.team.service.ICoachService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CoachServiceImpl implements ICoachService {

    private final ICoachRepository coachRepository;

    @Override
    public void create(Coach person) {
        Optional<Coach> personAlreadyExist = coachRepository.findByName(person.getName());

        if(personAlreadyExist.isPresent()) {
            throw new RuntimeException("Duplicate Exception TODO:");
        }


        coachRepository.save(person);
    }

    @Override
    public void update(Coach person) {
        coachRepository.save(person);
    }

    @Override
    public void delete(Coach person) {
        coachRepository.delete(person);
    }

    @Override
    public void deleteById(long id) {
        coachRepository.deleteById(id);
    }

    @Override
    public Coach findByName(String name) {
        return coachRepository.findByName(name).orElseThrow(
                () -> new RuntimeException("Not Found!!! TODO:"));
    }
}

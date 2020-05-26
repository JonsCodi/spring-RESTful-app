package org.restful.soccer_league.domains.team.service.impl;

import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.team.entity.Coach;
import org.restful.soccer_league.domains.team.entity.Person;
import org.restful.soccer_league.domains.team.entity.Player;
import org.restful.soccer_league.domains.team.repository.ICoachRepository;
import org.restful.soccer_league.domains.team.repository.IPersonRepository;
import org.restful.soccer_league.domains.team.repository.IPlayerRepository;
import org.restful.soccer_league.domains.team.service.IPersonService;
import org.restful.soccer_league.domains.utils.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PersonServiceImpl implements IPersonService {

    private final ICoachRepository coachRepository;
    private final IPlayerRepository playerRepository;
    private final IPersonRepository personBaseRepository;

    private final static String PERSONS = "Persons";

    @Override
    public Person createOrUpdate(Person person) {
        if (person instanceof Coach) {
            return coachRepository.save((Coach) person);
        } else {
            return playerRepository.save((Player) person);
        }
    }

    @Override
    public void update(Person person) {
        createOrUpdate(person);
    }

    @Override
    public void delete(Person person) {
        personBaseRepository.delete(person);
    }

    @Override
    public Person findById(Long id) {
        return personBaseRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(PERSONS, "Resource Not Found.")
        );
    }

    @Override
    public Page findAll(Pageable pageable) {
        return personBaseRepository.findAll(pageable);
    }


}

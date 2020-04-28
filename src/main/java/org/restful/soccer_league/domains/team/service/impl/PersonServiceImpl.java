package org.restful.soccer_league.domains.team.service.impl;

import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.team.entity.Coach;
import org.restful.soccer_league.domains.team.entity.Person;
import org.restful.soccer_league.domains.team.entity.Player;
import org.restful.soccer_league.domains.team.repository.ICoachRepository;
import org.restful.soccer_league.domains.team.repository.IPlayerRepository;
import org.restful.soccer_league.domains.team.service.IPersonService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PersonServiceImpl implements IPersonService {

    private final ICoachRepository coachRepository;
    private final IPlayerRepository playerRepository;

    @Override
    public Person create(Person person) {
        if (person instanceof Coach) {
            return coachRepository.save((Coach) person);
        } else {
            return playerRepository.save((Player) person);
        }
    }

    @Override
    public Person update(Person person) {
        return null;
    }

    @Override
    public void delete(Person person) {

    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public Person findByName(String name) {
        return null;
    }


}

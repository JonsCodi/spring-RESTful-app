package org.restful.soccer_league.domains.league.service.impl;

import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.league.entity.SoccerLeague;
import org.restful.soccer_league.domains.league.repository.ISoccerRepository;
import org.restful.soccer_league.domains.league.service.ISoccerLeagueService;
import org.restful.soccer_league.domains.utils.exceptions.ConflictException;
import org.restful.soccer_league.domains.utils.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SoccerLeagueServiceImpl implements ISoccerLeagueService {

    private final ISoccerRepository soccerRepository;

    @Override
    public SoccerLeague create(SoccerLeague soccerLeague) {
        Optional<SoccerLeague> soccerLeagueAlreadyExist = soccerRepository.findByName(soccerLeague.getName());

        if (soccerLeagueAlreadyExist.isPresent()) {
            throw new ConflictException("Already exist a Resource with same value of this field.", "name");
        }

        return soccerRepository.save(soccerLeague);
    }

    @Override
    public void update(SoccerLeague soccerLeague) {
        soccerRepository.save(soccerLeague);
    }

    @Override
    public void delete(SoccerLeague soccerLeague) {
        soccerRepository.delete(soccerLeague);
    }

    @Override
    public void deleteById(Long id) {
        soccerRepository.deleteById(id);
    }

    @Override
    public List<SoccerLeague> findAll() {
        return (List<SoccerLeague>) soccerRepository.findAll();
    }

    @Override
    public SoccerLeague findByName(String name) {
        return soccerRepository.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException("Resource not Found.")
        );
    }

    @Override
    public SoccerLeague findById(Long id) {
        return soccerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not Found.")
        );
    }

}

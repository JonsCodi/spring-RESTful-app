package org.restful.soccer_league.domains.league.service.impl;

import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.league.entity.SoccerLeague;
import org.restful.soccer_league.domains.league.repository.ISoccerRepository;
import org.restful.soccer_league.domains.league.service.ISoccerLeagueService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SoccerLeagueServiceImpl implements ISoccerLeagueService {

    private final ISoccerRepository soccerRepository;

    @Override
    public void create(SoccerLeague soccerLeague) {
        Optional<SoccerLeague> soccerLeagueAlreadyExist = soccerRepository.findByName(soccerLeague.getName());

        if(soccerLeagueAlreadyExist.isPresent()) {
            throw new RuntimeException("Duplicate Exception TODO:");
        }

        soccerRepository.save(soccerLeague);
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
}

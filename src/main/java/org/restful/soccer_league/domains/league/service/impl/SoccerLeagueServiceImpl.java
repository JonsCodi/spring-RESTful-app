package org.restful.soccer_league.domains.league.service.impl;

import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.league.entity.SoccerLeague;
import org.restful.soccer_league.domains.league.repository.ISoccerLeagueRepository;
import org.restful.soccer_league.domains.league.service.ISoccerLeagueService;
import org.restful.soccer_league.domains.utils.exceptions.ConflictException;
import org.restful.soccer_league.domains.utils.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SoccerLeagueServiceImpl implements ISoccerLeagueService {

    private final ISoccerLeagueRepository soccerRepository;

    private final static String SOCCER_LEAGUES = "Soccer-Leagues";

    @Override
    public SoccerLeague create(SoccerLeague soccerLeague) {
        Optional<SoccerLeague> soccerLeagueAlreadyExist = soccerRepository.findByName(soccerLeague.getName());

        if (soccerLeagueAlreadyExist.isPresent()) {
            throw new ConflictException("Already exist a Resource with same value of this field.", "name", SOCCER_LEAGUES);
        }

        return soccerRepository.save(soccerLeague);
    }

    @Override
    public void update(SoccerLeague soccerLeague) {
        soccerRepository.save(soccerLeague);
    }

    @Override
    public void deleteById(Long id) {
        soccerRepository.deleteById(id);
    }

    @Override
    public Page<SoccerLeague> findAll(Pageable pageable) {
        return soccerRepository.findAll(pageable);
    }

    @Override
    public Page<SoccerLeague> searchBySpecification(Specification<SoccerLeague> spec, Pageable pageable) {
        return soccerRepository.findAll(spec, pageable);
    }

    @Override
    public SoccerLeague findById(Long id) {
        return soccerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not Found.", SOCCER_LEAGUES)
        );
    }

}

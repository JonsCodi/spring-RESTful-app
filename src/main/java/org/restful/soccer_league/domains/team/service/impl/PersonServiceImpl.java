package org.restful.soccer_league.domains.team.service.impl;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.team.entity.Coach;
import org.restful.soccer_league.domains.team.entity.Person;
import org.restful.soccer_league.domains.team.entity.Player;
import org.restful.soccer_league.domains.team.repository.ICoachRepository;
import org.restful.soccer_league.domains.team.repository.IPersonRepository;
import org.restful.soccer_league.domains.team.repository.IPlayerRepository;
import org.restful.soccer_league.domains.team.service.IPersonService;
import org.restful.soccer_league.domains.utils.exceptions.ResourceNotFoundException;
import org.restful.soccer_league.domains.utils.search.CustomRSQLVisitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PersonServiceImpl implements IPersonService<Person> {

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

    @Transactional(readOnly = true)
    @Override
    public Page<Person> findAll(Pageable pageable) {
        return personBaseRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Person findByIdAndTeamId(Long id, Long idTeam) {
        Person person = searchForPerson(id, idTeam);

        return person;
    }

    private Person searchForPerson(Long id, Long idTeam) {
        Person person = null;
        Optional<Coach> coachOptional = coachRepository.findByIdAndTeamId(id, idTeam);

        if (coachOptional.isPresent()) {
            person = coachOptional.get();
        } else {
            Optional<Player> playerOptional = playerRepository.findByIdAndTeamId(id, idTeam);

            if (playerOptional.isPresent()) {
                person = playerOptional.get();
            }
        }

        if (Objects.isNull(person)) {
            throw new ResourceNotFoundException("Resource not found.", PERSONS);
        }

        return person;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Person> findAllByTeamId(Long idTeam, Pageable pageable) {
        List<Coach> coaches = coachRepository.findAllByTeamId(idTeam, pageable).getContent();
        List<Player> players = playerRepository.findAllByTeamId(idTeam, pageable).getContent();

        List<Person> persons = new ArrayList<>();
        persons.addAll(coaches);
        persons.addAll(players);

        Page<Person> page = new PageImpl<>(persons, pageable, coaches.size() + players.size());

        return page;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Person> searchBySpecification(Specification<Person> spec, Pageable pageable) {
        return personBaseRepository.findAll(spec, pageable);
    }

    @Override
    public Page<Person> findAllByTeamId(Long idTeam, String search, Pageable pageable) {
        Node rootNode = new RSQLParser().parse(search);

        Specification<Person> specPerson = rootNode.accept(new CustomRSQLVisitor<>(List.of(Player.class, Coach.class)));
        List<Person> persons = personBaseRepository.findAll(specPerson, pageable).getContent();


        /*

        TODO: Deve ser criado novos nodos a partir dos campos existentes, caso contrário o Hibernate não consegue excluir
            essa Specification e não retorna nenhum valor para fazer o where = ?
            Lógica:
                - Buscar somente os campos que existem nas Child Entities (Player, Coach) dentro do node.
                - Buscar seus Argumentos
                - Buscar o Operador Lógico
                - Criar novo Node com o new RSQLParser().parse(searchAfterCorrectFields)
            Deixar isso genérico para aceitar qualquer Entidade que há um Parent;

        Specification<Player> specPlayer = rootNode.accept(new CustomRSQLVisitor<>(List.of()));
        List<Player> players = playerRepository.findAll(specPlayer
                .and(PersonSpecification.searchForPlayerInTheTeam(idTeam)), pageable)
                .getContent();

        Specification<Coach> specCoach = rootNode.accept(new CustomRSQLVisitor<>(List.of(Player.class, Coach.class)));
        List<Coach> coaches = coachRepository.findAll(specCoach
                .and(PersonSpecification.searchForCoachInTheTeam(idTeam)), pageable)
                .getContent();
*/

        Page<Person> page = new PageImpl<>(persons, pageable, persons.size());

        return page;
    }

}

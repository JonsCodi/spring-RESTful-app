package org.restful.soccer_league.domains.team.api.v1.web;

import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.team.api.v1.web.request.BasePersonRequest;
import org.restful.soccer_league.domains.team.entity.Person;
import org.restful.soccer_league.domains.team.entity.Player;
import org.restful.soccer_league.domains.team.entity.Team;
import org.restful.soccer_league.domains.team.factory.PersonFactory;
import org.restful.soccer_league.domains.team.service.IPersonService;
import org.restful.soccer_league.domains.team.service.ITeamService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/teams/{name}/persons")
public class PersonHierarchyController {

    private final IPersonService<Person> personService;
    private final ITeamService teamService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> create(@PathVariable("name") String name, @RequestBody BasePersonRequest basePersonRequest) {
        Team team = teamService.findByName(name);

        Person person = PersonFactory.createPerson(basePersonRequest);

        person = personService.createOrUpdate(person);

        person = teamService.addPerson(team, person);

        return ResponseEntity.ok(person);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Person>> getAll(@PathVariable("name") String name) {
        Team team = teamService.findByName(name);

        List<Person> persons = new ArrayList<>(team.getPlayers());
        persons.add(team.getCoach());

        return ResponseEntity.ok(persons);
    }

    @GetMapping(path = "/{namePerson}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> get(@PathVariable("name") String name, @PathVariable("namePerson") String namePerson) {
        Team team = teamService.findByName(name);

        if (Objects.nonNull(team.getCoach()) && team.getCoach().getName().equals(namePerson)) {
            return ResponseEntity.ok(team.getCoach());
        } else {
            Optional<Player> player = team.getPlayers().stream().filter(playerFromDB -> playerFromDB.getName().equals(namePerson)).findAny();

            return player.<ResponseEntity<Person>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }
    }

    @PostMapping(path = "/{namePerson}")
    public ResponseEntity remove(@PathVariable("name") String name, @PathVariable("namePerson") String namePerson) {
        Team team = teamService.findByName(name);

        if (Objects.nonNull(team.getCoach()) && team.getCoach().getName().equals(namePerson)) {
            return removeCoach(team);
        } else {
            return removePlayer(namePerson, team);
        }

    }

    private ResponseEntity removePlayer(String namePerson, Team team) {

        Optional<Player> player = team.getPlayers().stream()
                .filter(playerFromDB -> playerFromDB.getName().equals(namePerson))
                .findAny();

        if (player.isPresent()) {
            team.getPlayers().remove(player.get());
            teamService.update(team);

            player.get().setTeam(null);
            personService.update(player.get());

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private ResponseEntity removeCoach(Team team) {
        team.getCoach().setTeam(null);
        personService.update(team.getCoach());

        team.setCoach(null);
        teamService.update(team);


        return ResponseEntity.ok().build();
    }


}

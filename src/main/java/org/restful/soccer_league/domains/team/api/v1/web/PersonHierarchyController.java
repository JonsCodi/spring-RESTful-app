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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/teams/{idTeam}/persons")
public class PersonHierarchyController {

    private final IPersonService<Person> personService;
    private final ITeamService teamService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> create(@PathVariable("idTeam") Long idTeam, @RequestBody BasePersonRequest basePersonRequest) {
        Team team = teamService.findById(idTeam);

        Person person = personService.createOrUpdate(PersonFactory.createPerson(basePersonRequest));

        teamService.addPerson(team, person);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(team.getId())
                .toUri();

        return ResponseEntity.created(location)
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Person>> getAll(@PathVariable("idTeam") Long idTeam) {
        Team team = teamService.findById(idTeam);

        List<Person> persons = new ArrayList<>(team.getPlayers());
        persons.add(team.getCoach());

        return ResponseEntity.ok(persons);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> get(@PathVariable("idTeam") Long idTeam, @PathVariable("id") Long id) {
        Team team = teamService.findById(idTeam);

        if(team.getCoach().getId().equals(id)){
            return ResponseEntity.ok(team.getCoach());
        }

        Optional<Player> person = team.getPlayers().stream().filter(player -> player.getId().equals(id)).findAny();

        if(person.isPresent()){
            return ResponseEntity.ok(person.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity remove(@PathVariable("idTeam") Long idTeam, @PathVariable("id") Long id) {
        Team team = teamService.findById(idTeam);
        Person person = personService.findById(id);

        teamService.removePerson(team, person);

        return ResponseEntity.ok().build();
    }

}

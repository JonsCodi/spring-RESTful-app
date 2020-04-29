package org.restful.soccer_league.domains.team.api.v1.web;

import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.team.api.v1.web.request.BasePersonRequest;
import org.restful.soccer_league.domains.team.entity.Person;
import org.restful.soccer_league.domains.team.entity.Player;
import org.restful.soccer_league.domains.team.entity.Team;
import org.restful.soccer_league.domains.team.factory.PersonFactory;
import org.restful.soccer_league.domains.team.service.IPersonService;
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
@RequestMapping("/persons")
public class PersonController {

    private final IPersonService<Person> personService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> create(@RequestBody BasePersonRequest basePersonRequest) {
        Person person = PersonFactory.createPerson(basePersonRequest);

        person = personService.createOrUpdate(person);

        return ResponseEntity.ok(person);
    }

    @GetMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> get(@PathVariable("name") String name) {
        return ResponseEntity.ok(personService.findByName(name));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Person>> getAll() {
        return ResponseEntity.ok(personService.findAll());
    }

    @PostMapping(path = "/{name}")
    public ResponseEntity delete(@PathVariable("name") String name) {
        Person person = personService.findByName(name);
        personService.delete(person);

        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/{name}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> update(@PathVariable("name") String name, @RequestBody BasePersonRequest basePersonRequest) {
        Person person = personService.findByName(name);

        Person personUpdated = PersonFactory.createPerson(basePersonRequest);
        personUpdated.setId(person.getId());

        return ResponseEntity.ok(personService.update(personUpdated));
    }

}

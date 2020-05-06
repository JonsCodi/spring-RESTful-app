package org.restful.soccer_league.domains.team.api.v1.web;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.team.api.v1.web.request.BasePersonRequest;
import org.restful.soccer_league.domains.team.entity.Coach;
import org.restful.soccer_league.domains.team.entity.Person;
import org.restful.soccer_league.domains.team.entity.Player;
import org.restful.soccer_league.domains.team.factory.PersonFactory;
import org.restful.soccer_league.domains.team.service.IPersonService;
import org.restful.soccer_league.domains.utils.components.PatchHelperComponent;
import org.restful.soccer_league.domains.utils.constants.PatchMediaType;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/persons")
public class PersonController {

    private final IPersonService<Person> personService;
    private final PatchHelperComponent patchHelperComponent;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> create(@RequestBody BasePersonRequest basePersonRequest) {
        Person person = personService.createOrUpdate(PersonFactory.createPerson(basePersonRequest));

        return ResponseEntity.ok(person);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Person>> getAll() {
        return ResponseEntity.ok(personService.findAll());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(personService.findById(id));
    }

    @PatchMapping(path = "/{id}", consumes = PatchMediaType.APPLICATION_JSON_PATCH_VALUE)
    public ResponseEntity<Person> update(@PathVariable("id") Long id, @RequestBody JsonPatch patchDocument) {
        Person person = personService.findById(id);

        Person personPatched = update(patchDocument, person);
        personService.update(personPatched);

        return ResponseEntity.noContent().build();
    }

    private Person update(JsonPatch patchDocument, Person person) {
        if (person instanceof Player) {
            return patchHelperComponent.applyPatch(patchDocument, (Player) person);
        } else {
            return patchHelperComponent.applyPatch(patchDocument, (Coach) person);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Person person = personService.findById(id);
        personService.delete(person);

        return ResponseEntity.ok().build();
    }


}

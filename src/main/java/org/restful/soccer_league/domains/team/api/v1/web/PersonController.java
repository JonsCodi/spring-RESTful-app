package org.restful.soccer_league.domains.team.api.v1.web;

import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(person.getId())
                .toUri();

        return ResponseEntity.created(location)
                .build();
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
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody JsonPatch jsonPatch) {
        Person person = personService.findById(id);
        Person personPatched = patchHelperComponent.applyPatch(jsonPatch, (Player) person);

        personService.update(personPatched);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/{id}", consumes = PatchMediaType.APPLICATION_MERGE_PATCH_VALUE)
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody JsonMergePatch jsonMergePatch) {
        Person person = personService.findById(id);
        Person personMerged = patchHelperComponent.applyMergePatch(jsonMergePatch, person);

        personService.update(personMerged);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Person person = personService.findById(id);
        personService.delete(person);

        return ResponseEntity.ok().build();
    }


}

package org.restful.soccer_league.domains.team.api.v1.web;

import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.team.api.v1.web.assembler.PersonModelAssembler;
import org.restful.soccer_league.domains.team.api.v1.web.model.PersonModel;
import org.restful.soccer_league.domains.team.api.v1.web.request.BasePersonRequest;
import org.restful.soccer_league.domains.team.entity.Coach;
import org.restful.soccer_league.domains.team.entity.Person;
import org.restful.soccer_league.domains.team.entity.Player;
import org.restful.soccer_league.domains.team.factory.PersonFactory;
import org.restful.soccer_league.domains.team.service.IPersonService;
import org.restful.soccer_league.domains.utils.api.web.v1.response.ResponseSuccessBody;
import org.restful.soccer_league.domains.utils.components.PatchHelperComponent;
import org.restful.soccer_league.domains.utils.components.ResponseEntityComponent;
import org.restful.soccer_league.domains.utils.constants.PatchMediaType;
import org.restful.soccer_league.domains.utils.enums.FieldsEnum;
import org.restful.soccer_league.domains.utils.enums.FiltersEnum;
import org.restful.soccer_league.domains.utils.search.CustomRSQLVisitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;

@RequiredArgsConstructor
@RestController
@RequestMapping("/persons")
public class PersonController {

    private final IPersonService<Person> personService;
    private final PatchHelperComponent patchHelperComponent;
    private final PagedResourcesAssembler<Person> personPagedResourcesAssembler;
    private final PersonModelAssembler personModelAssembler;
    private final ResponseEntityComponent responseEntityComponent;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> create(@RequestBody BasePersonRequest basePersonRequest) {
        Person person = personService.createOrUpdate(PersonFactory.createPerson(basePersonRequest));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(person.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseSuccessBody> getAll(@RequestParam(value = "fields", required = false, defaultValue = "all") String fields,
                                                      Pageable pageable) {
        this.responseEntityComponent.setJsonFilters(new FiltersEnum[]{FiltersEnum.COACH, FiltersEnum.PLAYER});

        Page<Person> persons = personService.findAll(pageable);

        if (persons.getContent().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        PagedModel<PersonModel> personModel = personPagedResourcesAssembler.toModel(persons, personModelAssembler);

        if (fields.equals(FieldsEnum.ALL.getField())) {
            return responseEntityComponent.returnAllContent(personModel.getContent(), personModel.getLinks(), personModel.getMetadata());
        }

        return responseEntityComponent.returnPartialContent(fields,
                personModel.getContent(), personModel.getLinks(), personModel.getMetadata());
    }

    @GetMapping(params = {"search"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseSuccessBody> search(@RequestParam(value = "fields", required = false, defaultValue = "all") String fields,
                                                      @RequestParam(value = "search", required = false) String search,
                                                      Pageable pageable) {
        this.responseEntityComponent.setJsonFilters(new FiltersEnum[]{FiltersEnum.PLAYER, FiltersEnum.COACH});

        Node rootNode = new RSQLParser().parse(search);
        Specification<Person> spec = rootNode.accept(new CustomRSQLVisitor<>(Arrays.asList(Player.class, Coach.class)));

        Page<Person> persons = personService.searchBySpecification(spec, pageable);
        PagedModel<PersonModel> pagePersonModel = personPagedResourcesAssembler.toModel(persons, personModelAssembler);

        if(pagePersonModel.getContent().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        if(fields.equals(FieldsEnum.ALL.getField())) {
            return responseEntityComponent.returnAllContent(pagePersonModel.getContent(), pagePersonModel.getLinks(), pagePersonModel.getMetadata());
        }

        return responseEntityComponent.returnPartialContent(fields,
                pagePersonModel.getContent(), pagePersonModel.getLinks(), pagePersonModel.getMetadata());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseSuccessBody> get(@RequestParam(value = "fields", required = false, defaultValue = "all") String fields,
                                      @PathVariable Long id) {
        this.responseEntityComponent.setJsonFilters(new FiltersEnum[]{FiltersEnum.PLAYER, FiltersEnum.COACH});

        Person person = personService.findById(id);
        PersonModel personModel = personModelAssembler.toModel(person);

        if (fields.equals(FieldsEnum.ALL.getField())) {
            return responseEntityComponent.returnAllContent(personModel, null, null);
        }

        return responseEntityComponent.returnPartialContent(fields,
                personModel, null, null);
    }

    @PatchMapping(path = "/{id}", consumes = PatchMediaType.APPLICATION_JSON_PATCH_VALUE)
    public ResponseEntity update(@PathVariable Long id, @RequestBody JsonPatch jsonPatch) {
        Person person = personService.findById(id);
        Person personPatched = patchHelperComponent.applyPatch(jsonPatch, person);

        personService.update(personPatched);

        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/{id}", consumes = PatchMediaType.APPLICATION_MERGE_PATCH_VALUE)
    public ResponseEntity update(@PathVariable Long id, @RequestBody JsonMergePatch jsonMergePatch) {
        Person person = personService.findById(id);
        Person personMerged = patchHelperComponent.applyMergePatch(jsonMergePatch, person);

        personService.update(personMerged);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        Person person = personService.findById(id);
        personService.delete(person);

        return ResponseEntity.ok().build();
    }

}

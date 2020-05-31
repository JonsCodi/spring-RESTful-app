package org.restful.soccer_league.domains.team.api.v1.web;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.team.api.v1.web.assembler.PersonModelAssembler;
import org.restful.soccer_league.domains.team.api.v1.web.model.PersonModel;
import org.restful.soccer_league.domains.team.api.v1.web.request.BasePersonRequest;
import org.restful.soccer_league.domains.team.entity.Person;
import org.restful.soccer_league.domains.team.entity.Team;
import org.restful.soccer_league.domains.team.factory.PersonFactory;
import org.restful.soccer_league.domains.team.service.IPersonService;
import org.restful.soccer_league.domains.team.service.ITeamService;
import org.restful.soccer_league.domains.utils.api.web.v1.response.ResponseSuccessBody;
import org.restful.soccer_league.domains.utils.components.PatchHelperComponent;
import org.restful.soccer_league.domains.utils.components.ResponseEntityComponent;
import org.restful.soccer_league.domains.utils.constants.PatchMediaType;
import org.restful.soccer_league.domains.utils.enums.FieldsEnum;
import org.restful.soccer_league.domains.utils.enums.FiltersEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/teams/{idTeam}/persons")
public class PersonHierarchyController {

    private final IPersonService<Person> personService;
    private final ITeamService teamService;
    private final PatchHelperComponent patchHelperComponent;
    private final PagedResourcesAssembler<Person> personPagedResourcesAssembler;
    private final PersonModelAssembler personModelAssembler;
    private final ResponseEntityComponent responseEntityComponent;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@PathVariable Long idTeam, @Valid @RequestBody BasePersonRequest basePersonRequest) {
        Team team = teamService.findById(idTeam);

        Person person = personService.createOrUpdate(PersonFactory.createPerson(basePersonRequest));

        teamService.addPerson(team, person);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(person.getId())
                .toUri();

        return ResponseEntity.created(location)
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseSuccessBody> getAll(@PathVariable Long idTeam,
                                                      @RequestParam(value = "fields", required = false, defaultValue = "all") String fields,
                                                      Pageable pageable) {
        this.responseEntityComponent.setJsonFilters(new FiltersEnum[]{FiltersEnum.COACH, FiltersEnum.PLAYER});

        Page<Person> personsFromTeam = personService.findAllByTeamId(idTeam, pageable);

        if (personsFromTeam.getContent().isEmpty()) {
            isTeamNotExistThenThrowsNotFoundException(idTeam);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        PagedModel<PersonModel> personModel = personPagedResourcesAssembler.toModel(personsFromTeam, personModelAssembler);

        if (fields.equals(FieldsEnum.ALL.getField())) {
            return responseEntityComponent.returnAllContent(personModel.getContent(), personModel.getLinks(), personModel.getMetadata());
        }

        return responseEntityComponent.returnPartialContent(fields,
                personModel.getContent(), personModel.getLinks(), personModel.getMetadata());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseSuccessBody> get(@PathVariable Long idTeam,
                                                   @PathVariable Long id,
                                                   @RequestParam(value = "fields", required = false, defaultValue = "all") String fields) {
        isTeamNotExistThenThrowsNotFoundException(idTeam);

        this.responseEntityComponent.setJsonFilters(new FiltersEnum[]{FiltersEnum.COACH, FiltersEnum.PLAYER});

        Person person = personService.findByIdAndTeamId(id, idTeam);
        PersonModel personModel = personModelAssembler.toModel(person);

        if (fields.equals(FieldsEnum.ALL.getField())) {
            return responseEntityComponent.returnAllContent(personModel, null, null);
        }

        return responseEntityComponent.returnPartialContent(fields,
                personModel, null, null);
    }

    @PatchMapping(path = "/{id}", consumes = PatchMediaType.APPLICATION_JSON_PATCH_VALUE)
    public ResponseEntity update(@PathVariable Long idTeam, @PathVariable Long id, @RequestBody JsonPatch jsonPatch) {
        isTeamNotExistThenThrowsNotFoundException(idTeam);

        Person person = personService.findByIdAndTeamId(id, idTeam);
        Person personPatched = patchHelperComponent.applyPatch(jsonPatch, person);

        personService.update(personPatched);

        return ResponseEntity.ok().build();
    }

    private void isTeamNotExistThenThrowsNotFoundException(Long idTeam) {
        teamService.findById(idTeam);
    }

    /**
     * Método não mais necessário, por motivos do {@link PatchMapping} suportar a operação 'remove' nativamente
     * e por isso este deve ser usado ao invés do {@link DeleteMapping}.
     *
     * @Deprecated
     */
    @Deprecated
    @DeleteMapping(path = "/{id}")
    public ResponseEntity remove(@PathVariable Long idTeam, @PathVariable Long id) {
        Team team = teamService.findById(idTeam);
        Person person = personService.findById(id);

        teamService.removePerson(team, person);

        return ResponseEntity.ok().build();
    }

}

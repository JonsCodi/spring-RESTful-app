package org.restful.soccer_league.domains.team.api.v1.web;

import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.team.api.v1.web.assembler.PersonModelAssembler;
import org.restful.soccer_league.domains.team.api.v1.web.model.PersonModel;
import org.restful.soccer_league.domains.team.api.v1.web.request.BasePersonRequest;
import org.restful.soccer_league.domains.team.entity.Person;
import org.restful.soccer_league.domains.team.entity.Player;
import org.restful.soccer_league.domains.team.entity.Team;
import org.restful.soccer_league.domains.team.factory.PersonFactory;
import org.restful.soccer_league.domains.team.service.IPersonService;
import org.restful.soccer_league.domains.team.service.ITeamService;
import org.restful.soccer_league.domains.utils.api.web.v1.response.ResponseSuccessBody;
import org.restful.soccer_league.domains.utils.components.ResponseEntityComponent;
import org.restful.soccer_league.domains.utils.enums.FieldsEnum;
import org.restful.soccer_league.domains.utils.enums.FiltersEnum;
import org.restful.soccer_league.domains.utils.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/teams/{idTeam}/persons")
public class PersonHierarchyController {

    private final IPersonService<Person> personService;
    private final ITeamService teamService;
    private final PagedResourcesAssembler<Person> personPagedResourcesAssembler;
    private final PersonModelAssembler personModelAssembler;
    private final ResponseEntityComponent responseEntityComponent;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> create(@PathVariable Long idTeam, @Valid  @RequestBody BasePersonRequest basePersonRequest) {
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

        if(personsFromTeam.getContent().isEmpty()) {
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

    private void isTeamNotExistThenThrowsNotFoundException(Long idTeam) {
        teamService.findById(idTeam);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> get(@PathVariable Long idTeam, @PathVariable Long id) {
        Team team = teamService.findById(idTeam);

        if(Objects.nonNull(team.getCoach()) && team.getCoach().getId().equals(id)){
            return ResponseEntity.ok(team.getCoach());
        }

        Optional<Player> person = team.getPlayers().stream().filter(player -> player.getId().equals(id)).findAny();

        if(person.isPresent()){
            return ResponseEntity.ok(person.get());
        }

        throw new ResourceNotFoundException("Resource not found", "Teams.Persons");
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity remove(@PathVariable Long idTeam, @PathVariable Long id) {
        Team team = teamService.findById(idTeam);
        Person person = personService.findById(id);

        teamService.removePerson(team, person);

        return ResponseEntity.ok().build();
    }

}

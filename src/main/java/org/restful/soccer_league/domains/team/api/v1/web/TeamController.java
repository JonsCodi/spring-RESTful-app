package org.restful.soccer_league.domains.team.api.v1.web;

import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.team.api.v1.web.assembler.TeamModelAssembler;
import org.restful.soccer_league.domains.team.api.v1.web.model.TeamModel;
import org.restful.soccer_league.domains.team.api.v1.web.request.TeamCreateRequest;
import org.restful.soccer_league.domains.team.entity.Team;
import org.restful.soccer_league.domains.team.factory.TeamFactory;
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
@RequestMapping("/teams")
public class TeamController {

    private final ITeamService teamService;
    private final PatchHelperComponent patchHelperComponent;
    private final PagedResourcesAssembler<Team> teamResourcesAssembler;
    private final TeamModelAssembler teamModelAssembler;

    private final ResponseEntityComponent responseEntityComponent;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@Valid @RequestBody TeamCreateRequest teamCreateRequest) {
        Team team = teamService.create(TeamFactory.createTeam(teamCreateRequest));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(team.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseSuccessBody> getAll(@RequestParam(value = "fields", required = false, defaultValue = "all") String fields,
                                                      Pageable pageable) {
        Page<Team> teams = teamService.findAll(pageable);
        PagedModel<TeamModel> teamModel = teamResourcesAssembler.toModel(teams, teamModelAssembler);

        if(fields.equals(FieldsEnum.ALL.getField())) {
            return responseEntityComponent.returnAllContent(FiltersEnum.TEAM, teamModel.getContent(), teamModel.getLinks(), teamModel.getMetadata());
        }

        return responseEntityComponent.returnPartialContent(FiltersEnum.TEAM, fields,
                teamModel.getContent(), teamModel.getLinks(), teamModel.getMetadata());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseSuccessBody> get(@RequestParam(value = "fields", required = false, defaultValue = "all") String fields,
                                                   @PathVariable Long id) {
        Team team = teamService.findById(id);
        TeamModel teamModel = teamModelAssembler.toModel(team);

        if(fields.equals(FieldsEnum.ALL.getField())){
            return responseEntityComponent.returnAllContent(FiltersEnum.TEAM, teamModel, null, null);
        }

        return responseEntityComponent.returnPartialContent(FiltersEnum.TEAM, fields,
                teamModel, null, null);
    }

    @PatchMapping(path = "/{id}", consumes = PatchMediaType.APPLICATION_JSON_PATCH_VALUE)
    public ResponseEntity update(@PathVariable Long id, @RequestBody JsonPatch jsonPatch) {
        Team team = teamService.findById(id);
        Team teamPatched = patchHelperComponent.applyPatch(jsonPatch, team);

        teamService.update(teamPatched);

        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/{id}", consumes = PatchMediaType.APPLICATION_MERGE_PATCH_VALUE)
    public ResponseEntity update(@PathVariable Long id, @RequestBody JsonMergePatch jsonMergePatch) {
        Team team = teamService.findById(id);
        Team teamMerged = patchHelperComponent.applyMergePatch(jsonMergePatch, team);

        teamService.update(teamMerged);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        teamService.deleteById(id);

        return ResponseEntity.ok().build();
    }

}

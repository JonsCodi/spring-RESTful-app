package org.restful.soccer_league.domains.team.api.v1.web;

import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.team.api.v1.web.request.TeamCreateRequest;
import org.restful.soccer_league.domains.team.entity.Team;
import org.restful.soccer_league.domains.team.factory.TeamFactory;
import org.restful.soccer_league.domains.team.service.ITeamService;
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

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/teams")
public class TeamController {

    private final ITeamService teamService;
    private final PatchHelperComponent patchHelperComponent;

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
    public ResponseEntity<List<Team>> getAll() {
        return ResponseEntity.ok(teamService.findAll());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Team> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(teamService.findById(id));
    }

    @PatchMapping(path = "/{id}", consumes = PatchMediaType.APPLICATION_JSON_PATCH_VALUE)
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody JsonPatch jsonPatch) {
        Team team = teamService.findById(id);
        Team teamPatched = patchHelperComponent.applyPatch(jsonPatch, team);

        teamService.update(teamPatched);

        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/{id}", produces = PatchMediaType.APPLICATION_MERGE_PATCH_VALUE)
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody JsonMergePatch jsonMergePatch){
        Team team = teamService.findById(id);
        Team teamMerged = patchHelperComponent.applyMergePatch(jsonMergePatch, team);

        teamService.update(teamMerged);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        teamService.deleteById(id);

        return ResponseEntity.ok().build();
    }

}

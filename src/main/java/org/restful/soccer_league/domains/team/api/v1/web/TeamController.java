package org.restful.soccer_league.domains.team.api.v1.web;

import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.team.api.v1.web.request.TeamCreateRequest;
import org.restful.soccer_league.domains.team.api.v1.web.request.TeamUpdateRequest;
import org.restful.soccer_league.domains.team.entity.Team;
import org.restful.soccer_league.domains.team.factory.RecordFactory;
import org.restful.soccer_league.domains.team.factory.TeamFactory;
import org.restful.soccer_league.domains.team.service.ITeamService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/teams")
public class TeamController {

    private final ITeamService teamService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Team> create(@RequestBody TeamCreateRequest teamCreateRequest) {
        Team team = TeamFactory.createTeam(teamCreateRequest);

        return ResponseEntity.ok(teamService.create(team));
    }

    @GetMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Team> get(@PathVariable("name") String name){
        return ResponseEntity.ok(teamService.findByName(name));
    }

    @PostMapping(path = "/{name}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Team> update(@PathVariable("name") String name, @RequestBody TeamUpdateRequest teamUpdateRequest) {
        Team teamForUpdate = teamService.findByName(name);

        teamForUpdate.setName(teamUpdateRequest.getName());
        if(Objects.nonNull(teamUpdateRequest.getRecord()))
            teamForUpdate.setRecord(RecordFactory.createRecord(teamUpdateRequest.getRecord()));

        return ResponseEntity.ok(teamService.update(teamForUpdate));
    }

    @PostMapping(path = "/{name}")
    public ResponseEntity delete(@PathVariable("name") String name) {
        Team teamForDelete = teamService.findByName(name);

        teamService.delete(teamForDelete);

        return ResponseEntity.ok().build();
    }

}

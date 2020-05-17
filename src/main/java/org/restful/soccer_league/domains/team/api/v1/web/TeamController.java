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
import org.restful.soccer_league.domains.utils.pageable.OffsetBasedPageRequest;
import org.restful.soccer_league.domains.utils.response.LinkEnum;
import org.restful.soccer_league.domains.utils.response.LinkPage;
import org.restful.soccer_league.domains.utils.response.ResponseSuccessBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/teams")
public class TeamController {

    private final ITeamService teamService;
    private final PatchHelperComponent patchHelperComponent;

    @Value("${service.base_url}")
    private String baseUrl;

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
    public ResponseEntity<ResponseSuccessBody> getAll(@RequestParam(value = "limit", defaultValue = "10") int limit,
                                                      @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                      @RequestParam(value = "order", defaultValue = "ASC", required = false) String order,
                                                      @RequestParam(value = "sortBy", required = false) String... sortByParams) {
        Pageable pageable = new OffsetBasedPageRequest(limit, offset, Sort.Direction.fromString(order), sortByParams);
        Page<Team> page = teamService.findAll(pageable);
        List<Team> teams = page.getContent();

        //TODO: Falta ainda criar a estrutura de Links da sua paginação.. é complicadinho..
        List<LinkPage> linkPages = Arrays.asList(
                new LinkPage(baseUrl.concat("/teams?offset=0"), HttpMethod.GET.name(), LinkEnum.FIRST.getRel()),
                new LinkPage(baseUrl.concat("/teams?offset=").concat(String.valueOf(pageable.previousOrFirst().getOffset())),
                        HttpMethod.GET.name(), LinkEnum.PREV.getRel()),
                new LinkPage(baseUrl.concat("/teams?offset=").concat(String.valueOf(pageable.next().getOffset())),
                        HttpMethod.GET.name(), LinkEnum.NEXT.getRel()),
                new LinkPage(baseUrl.concat("/teams?offset=").concat(String.valueOf(page.nextOrLastPageable().getOffset())),
                        HttpMethod.GET.name(), LinkEnum.LAST.getRel())
        );

        ResponseSuccessBody successBody = new ResponseSuccessBody(
                linkPages, teams.size(), page.getTotalElements(), teams
        );

        return ResponseEntity.ok(successBody);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Team> get(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.findById(id));
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

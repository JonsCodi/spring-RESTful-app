package org.restful.soccer_league.domains.league.api.v1.web;

import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.league.api.v1.web.model.SoccerLeagueModel;
import org.restful.soccer_league.domains.league.api.v1.web.model.SoccerLeagueModelAssembler;
import org.restful.soccer_league.domains.league.api.v1.web.request.SoccerLeagueRequest;
import org.restful.soccer_league.domains.league.entity.SoccerLeague;
import org.restful.soccer_league.domains.league.factory.SoccerLeagueFactory;
import org.restful.soccer_league.domains.league.service.ISoccerLeagueService;
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
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/soccer-leagues")
public class SoccerLeagueController {

    private final ISoccerLeagueService soccerLeagueService;
    private final PatchHelperComponent patchHelperComponent;
    private final PagedResourcesAssembler<SoccerLeague> soccerLeaguePagedResourcesAssembler;
    private final SoccerLeagueModelAssembler soccerLeagueModelAssembler;
    private final ResponseEntityComponent responseEntityComponent;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody SoccerLeagueRequest soccerLeagueRequest) {
        SoccerLeague soccerLeague = soccerLeagueService.create(SoccerLeagueFactory.createSoccerLeagueObject(soccerLeagueRequest));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(soccerLeague.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseSuccessBody> getAll(@RequestParam(value = "fields", required = false, defaultValue = "all") String fields,
                                                      Pageable pageable) {
        this.responseEntityComponent.setJsonFilters(new FiltersEnum[]{FiltersEnum.SOCCER_LEAGUE});

        Page<SoccerLeague> soccerLeagues = soccerLeagueService.findAll(pageable);

        if (soccerLeagues.getContent().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        PagedModel<SoccerLeagueModel> soccerLeagueModel = soccerLeaguePagedResourcesAssembler.toModel(soccerLeagues, soccerLeagueModelAssembler);

        if (fields.equals(FieldsEnum.ALL.getField())) {
            return responseEntityComponent.returnAllContent(soccerLeagueModel.getContent(), soccerLeagueModel.getLinks(), soccerLeagueModel.getMetadata());
        }

        return responseEntityComponent.returnPartialContent(fields,
                soccerLeagueModel.getContent(), soccerLeagueModel.getLinks(), soccerLeagueModel.getMetadata());
    }

    @GetMapping(params = {"search"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseSuccessBody> search(@RequestParam(value = "fields", required = false, defaultValue = "all") String fields,
                                                      @RequestParam(value = "search", required = false) String search,
                                                      Pageable pageable) {
        this.responseEntityComponent.setJsonFilters(new FiltersEnum[]{FiltersEnum.SOCCER_LEAGUE});

        Node rootNode = new RSQLParser().parse(search);
        Specification<SoccerLeague> spec = rootNode.accept(new CustomRSQLVisitor<>(List.of()));

        Page<SoccerLeague> soccerLeagues = soccerLeagueService.searchBySpecification(spec, pageable);
        PagedModel<SoccerLeagueModel> pageSoccerLeague = soccerLeaguePagedResourcesAssembler.toModel(soccerLeagues, soccerLeagueModelAssembler);

        if (pageSoccerLeague.getContent().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        if (fields.equals(FieldsEnum.ALL.getField())) {
            return responseEntityComponent.returnAllContent(pageSoccerLeague.getContent(), pageSoccerLeague.getLinks(), pageSoccerLeague.getMetadata());
        }

        return responseEntityComponent.returnPartialContent(fields,
                pageSoccerLeague.getContent(), pageSoccerLeague.getLinks(), pageSoccerLeague.getMetadata());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseSuccessBody> get(@RequestParam(value = "fields", required = false, defaultValue = "all") String fields,
                                                   @PathVariable Long id) {
        this.responseEntityComponent.setJsonFilters(new FiltersEnum[]{FiltersEnum.SOCCER_LEAGUE});

        SoccerLeague soccerLeague = soccerLeagueService.findById(id);
        SoccerLeagueModel soccerLeagueModel = soccerLeagueModelAssembler.toModel(soccerLeague);

        if (fields.equals(FieldsEnum.ALL.getField())) {
            return responseEntityComponent.returnAllContent(soccerLeagueModel, null, null);
        }

        return responseEntityComponent.returnPartialContent(fields, soccerLeagueModel, null, null);
    }

    @PatchMapping(path = "/{id}", consumes = PatchMediaType.APPLICATION_JSON_PATCH_VALUE)
    public ResponseEntity update(@PathVariable Long id, @RequestBody JsonPatch jsonPatch) {
        SoccerLeague soccerLeague = soccerLeagueService.findById(id);
        SoccerLeague soccerLeaguePatched = patchHelperComponent.applyPatch(jsonPatch, soccerLeague);

        soccerLeagueService.update(soccerLeaguePatched);

        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/{id}", consumes = PatchMediaType.APPLICATION_MERGE_PATCH_VALUE)
    public ResponseEntity update(@PathVariable Long id, @RequestBody JsonMergePatch jsonMergePatch) {
        SoccerLeague soccerLeague = soccerLeagueService.findById(id);
        SoccerLeague soccerLeagueMerged = patchHelperComponent.applyMergePatch(jsonMergePatch, soccerLeague);

        soccerLeagueService.update(soccerLeagueMerged);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        soccerLeagueService.deleteById(id);

        return ResponseEntity.ok().build();
    }

}

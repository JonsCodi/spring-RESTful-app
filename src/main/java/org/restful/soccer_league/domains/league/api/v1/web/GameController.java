package org.restful.soccer_league.domains.league.api.v1.web;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.league.api.v1.web.model.GameModel;
import org.restful.soccer_league.domains.league.api.v1.web.model.GameModelAssembler;
import org.restful.soccer_league.domains.league.entity.Game;
import org.restful.soccer_league.domains.league.service.IGameService;
import org.restful.soccer_league.domains.utils.api.web.v1.response.ResponseSuccessBody;
import org.restful.soccer_league.domains.utils.components.ResponseEntityComponent;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/soccer-leagues/{id}/games")
public class GameController {

    private final IGameService gameService;
    private final PagedResourcesAssembler<Game> gamePagedResourcesAssembler;
    private final GameModelAssembler gameModelAssembler;
    private final ResponseEntityComponent responseEntityComponent;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseSuccessBody> search(@RequestParam("fields") String fields,
                                                      @RequestParam("search") String search,
                                                      Pageable pageable) {
        this.responseEntityComponent.setJsonFilters(new FiltersEnum[]{FiltersEnum.GAME});

        Node rootNode = new RSQLParser().parse(search);
        Specification<Game> spec = rootNode.accept(new CustomRSQLVisitor<>(List.of()));

        Page<Game> games = gameService.searchBySpecification(spec, pageable);
        PagedModel<GameModel> pageGames = gamePagedResourcesAssembler.toModel(games, gameModelAssembler);

        if (pageGames.getContent().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        if (fields.equals(FieldsEnum.ALL.getField())) {
            return responseEntityComponent.returnAllContent(pageGames.getContent(), pageGames.getLinks(), pageGames.getMetadata());
        }

        return responseEntityComponent.returnPartialContent(fields,
                pageGames.getContent(), pageGames.getLinks(), pageGames.getMetadata());
    }


}

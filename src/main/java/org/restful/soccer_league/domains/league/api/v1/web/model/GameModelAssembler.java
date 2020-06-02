package org.restful.soccer_league.domains.league.api.v1.web.model;

import org.restful.soccer_league.domains.league.api.v1.web.GameController;
import org.restful.soccer_league.domains.league.entity.Game;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class GameModelAssembler extends RepresentationModelAssemblerSupport<Game, GameModel> {

    public GameModelAssembler() {
        super(GameController.class, GameModel.class);
    }

    @Override
    public GameModel toModel(Game entity) {
        GameModel gameModel = instantiateModel(entity);

        gameModel.setLocation(entity.getLocation());
        gameModel.setTeamA(entity.getTeamA());
        gameModel.setTeamB(entity.getTeamB());
        gameModel.setScoreTeamA(entity.getScoreTeamA());
        gameModel.setScoreTeamB(entity.getScoreTeamB());
        gameModel.setCreatedAt(entity.getCreatedAt());
        gameModel.setDisabledAt(entity.getDisabledAt());
        gameModel.setDisabledAt(entity.getDisabledAt());

        return gameModel;
    }
}

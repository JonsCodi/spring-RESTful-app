package org.restful.soccer_league.domains.league.api.v1.web.model;

import org.restful.soccer_league.domains.league.api.v1.web.SoccerLeagueController;
import org.restful.soccer_league.domains.league.entity.SoccerLeague;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class SoccerLeagueModelAssembler extends RepresentationModelAssemblerSupport<SoccerLeague, SoccerLeagueModel> {

    public SoccerLeagueModelAssembler() {
        super(SoccerLeagueController.class, SoccerLeagueModel.class);
    }

    @Override
    public SoccerLeagueModel toModel(SoccerLeague entity) {
        return null;
    }
}

package org.restful.soccer_league.domains.league.api.v1.web.model;

import org.apache.logging.log4j.util.Strings;
import org.restful.soccer_league.domains.league.api.v1.web.SoccerLeagueController;
import org.restful.soccer_league.domains.league.entity.SoccerLeague;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SoccerLeagueModelAssembler extends RepresentationModelAssemblerSupport<SoccerLeague, SoccerLeagueModel> {

    public SoccerLeagueModelAssembler() {
        super(SoccerLeagueController.class, SoccerLeagueModel.class);
    }

    @Override
    public SoccerLeagueModel toModel(SoccerLeague entity) {
        SoccerLeagueModel soccerLeagueModel = instantiateModel(entity);

        soccerLeagueModel.add(linkTo(methodOn(SoccerLeagueController.class)
                .get(Strings.EMPTY, entity.getId()))
                .withSelfRel());

        soccerLeagueModel.setId(entity.getId());
        soccerLeagueModel.setName(entity.getName());
        soccerLeagueModel.setCreatedAt(entity.getCreatedAt());
        soccerLeagueModel.setUpdatedAt(entity.getUpdatedAt());
        soccerLeagueModel.setDisabledAt(entity.getDisabledAt());

        return soccerLeagueModel;
    }
}

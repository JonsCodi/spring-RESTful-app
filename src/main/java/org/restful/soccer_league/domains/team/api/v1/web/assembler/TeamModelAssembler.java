package org.restful.soccer_league.domains.team.api.v1.web.assembler;

import org.restful.soccer_league.domains.team.api.v1.web.TeamController;
import org.restful.soccer_league.domains.team.api.v1.web.model.TeamModel;
import org.restful.soccer_league.domains.team.entity.Team;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TeamModelAssembler extends RepresentationModelAssemblerSupport<Team, TeamModel> {

    public TeamModelAssembler() {
        super(TeamController.class, TeamModel.class);
    }

    @Override
    public TeamModel toModel(Team entity) {

        TeamModel teamModel = instantiateModel(entity);

        teamModel.add(linkTo(methodOn(TeamController.class)
                .get(entity.getId())).withSelfRel());

        teamModel.setId(entity.getId());
        teamModel.setName(entity.getName());
        teamModel.setCaptain(entity.getCaptain());
        teamModel.setRecord(entity.getRecord());
        teamModel.setCreatedAt(entity.getCreatedAt());
        teamModel.setUpdatedAt(entity.getUpdatedAt());
        teamModel.setDisabledAt(entity.getDisabledAt());

        return teamModel;
    }

}

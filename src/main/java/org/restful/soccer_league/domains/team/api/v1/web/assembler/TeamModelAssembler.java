package org.restful.soccer_league.domains.team.api.v1.web.assembler;

import org.apache.logging.log4j.util.Strings;
import org.restful.soccer_league.domains.league.api.v1.web.SoccerLeagueController;
import org.restful.soccer_league.domains.team.api.v1.web.PersonController;
import org.restful.soccer_league.domains.team.api.v1.web.PersonHierarchyController;
import org.restful.soccer_league.domains.team.api.v1.web.TeamController;
import org.restful.soccer_league.domains.team.api.v1.web.model.TeamModel;
import org.restful.soccer_league.domains.team.entity.Team;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
                .get(Strings.EMPTY, entity.getId()))
                .withSelfRel());

        if (Objects.nonNull(entity.getCoach())) {
            teamModel.add(linkTo(methodOn(PersonController.class)
                    .get(Strings.EMPTY, entity.getCoach().getId()))
                    .withRel("coach"));
        }

        if (!entity.getPlayers().isEmpty()) {
            teamModel.add(linkTo(methodOn(PersonHierarchyController.class)
                    .search(entity.getId(),
                            Strings.EMPTY,
                            "personType==PLAYER",
                            null))
                    .withRel("players"));
        }

        if (!entity.getSoccerLeagues().isEmpty()) {
            String searchForLeagues = "teams.id==".concat(String.valueOf(entity.getId()));

            teamModel.add(linkTo(methodOn(SoccerLeagueController.class)
                    .search(Strings.EMPTY,
                            searchForLeagues,
                            null))
                    .withRel("leagues"));
        }

        if (Objects.nonNull(entity.getCaptain())) {
            String searchForCaptain = "personType==PLAYER;isCaptain==true";

            teamModel.add(linkTo(methodOn(PersonHierarchyController.class)
                    .search(entity.getId(),
                            Strings.EMPTY,
                            searchForCaptain,
                            null))
                    .withRel("leagues"));
        }

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

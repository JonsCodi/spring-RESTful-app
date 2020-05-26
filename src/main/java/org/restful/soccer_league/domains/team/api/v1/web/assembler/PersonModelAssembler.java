package org.restful.soccer_league.domains.team.api.v1.web.assembler;

import org.restful.soccer_league.domains.team.api.v1.web.PersonController;
import org.restful.soccer_league.domains.team.api.v1.web.model.PersonModel;
import org.restful.soccer_league.domains.team.entity.Coach;
import org.restful.soccer_league.domains.team.entity.Person;
import org.restful.soccer_league.domains.team.entity.Player;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PersonModelAssembler extends RepresentationModelAssemblerSupport<Person, PersonModel> {

    private final PlayerModelAssembler playerModelAssembler;
    private final CoachModelAssembler coachModelAssembler;

    public PersonModelAssembler(PlayerModelAssembler playerModelAssembler, CoachModelAssembler coachModelAssembler) {
        super(PersonController.class, PersonModel.class);

        this.playerModelAssembler = playerModelAssembler;
        this.coachModelAssembler = coachModelAssembler;
    }

    @Override
    public PersonModel toModel(Person entity) {
        if(entity instanceof Player){
            return playerModelAssembler.toModel((Player) entity);
        }else {
            return coachModelAssembler.toModel((Coach) entity);
        }
    }

}

package org.restful.soccer_league.domains.team.api.v1.web.assembler;

import org.restful.soccer_league.domains.team.api.v1.web.PersonController;
import org.restful.soccer_league.domains.team.api.v1.web.model.CoachModel;
import org.restful.soccer_league.domains.team.entity.Coach;
import org.springframework.context.annotation.Lazy;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Lazy
@Component
public class CoachModelAssembler extends RepresentationModelAssemblerSupport<Coach, CoachModel> {

    public CoachModelAssembler() {
        super(PersonController.class, CoachModel.class);
    }

    @Override
    public CoachModel toModel(Coach entity) {
        CoachModel coachModel = instantiateModel(entity);

        coachModel.add(linkTo(methodOn(PersonController.class)
                .get(entity.getId())).withSelfRel());

        coachModel.setId(entity.getId());
        coachModel.setName(entity.getName());
        coachModel.setAddress(entity.getAddress());
        coachModel.setCreatedAt(entity.getCreatedAt());
        coachModel.setDisabledAt(entity.getDisabledAt());
        coachModel.setUpdatedAt(entity.getUpdatedAt());

        coachModel.setAccrLevel(entity.getAccrLevel());
        coachModel.setExperience(entity.getExperience());

        return coachModel;
    }

}

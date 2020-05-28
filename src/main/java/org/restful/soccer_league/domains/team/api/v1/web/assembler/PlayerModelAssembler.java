package org.restful.soccer_league.domains.team.api.v1.web.assembler;

import org.restful.soccer_league.domains.team.api.v1.web.PersonController;
import org.restful.soccer_league.domains.team.api.v1.web.model.PlayerModel;
import org.restful.soccer_league.domains.team.entity.Player;
import org.restful.soccer_league.domains.utils.enums.FieldsEnum;
import org.springframework.context.annotation.Lazy;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Lazy
@Component
public class PlayerModelAssembler extends RepresentationModelAssemblerSupport<Player, PlayerModel> {

    public PlayerModelAssembler() {
        super(PersonController.class, PlayerModel.class);
    }

    @Override
    public PlayerModel toModel(Player entity) {
        PlayerModel playerModel = instantiateModel(entity);

        playerModel.add(linkTo(methodOn(PersonController.class)
                .get(FieldsEnum.ALL.getField(), entity.getId())).withSelfRel());

        playerModel.setId(entity.getId());
        playerModel.setName(entity.getName());
        playerModel.setAddress(entity.getAddress());
        playerModel.setCreatedAt(entity.getCreatedAt());
        playerModel.setDisabledAt(entity.getDisabledAt());
        playerModel.setUpdatedAt(entity.getUpdatedAt());

        playerModel.setNumber(entity.getNumber());
        playerModel.setPosition(entity.getPosition());
        playerModel.setCaptain(entity.isCaptain());

        return playerModel;
    }
}

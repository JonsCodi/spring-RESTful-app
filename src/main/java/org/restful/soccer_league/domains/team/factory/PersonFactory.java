package org.restful.soccer_league.domains.team.factory;

import org.restful.soccer_league.domains.team.api.v1.web.request.BasePersonRequest;
import org.restful.soccer_league.domains.team.api.v1.web.request.CoachCreateRequest;
import org.restful.soccer_league.domains.team.api.v1.web.request.PlayerCreateRequest;
import org.restful.soccer_league.domains.team.entity.Coach;
import org.restful.soccer_league.domains.team.entity.Person;
import org.restful.soccer_league.domains.team.entity.Player;

public final class PersonFactory {

    public static Person createPerson(BasePersonRequest personRequest) {
        if (personRequest instanceof PlayerCreateRequest) {
            return createPlayer((PlayerCreateRequest) personRequest);
        } else if (personRequest instanceof CoachCreateRequest) {
            return createCoach((CoachCreateRequest) personRequest);
        }

        throw new RuntimeException("Person Type: ERROR");
    }

    private static Person createCoach(CoachCreateRequest personRequest) {
        return new Coach(personRequest.getName(), personRequest.getAddress(),
                personRequest.getAccrLevel().name(), personRequest.getExperience());
    }

    private static Person createPlayer(PlayerCreateRequest personRequest) {
        return new Player(personRequest.getName(), personRequest.getAddress(),
                personRequest.getNumber(), personRequest.getPosition().name(), personRequest.isCaptain());
    }
}

package org.restful.soccer_league.domains.team.specification;

import org.restful.soccer_league.domains.team.entity.Coach;
import org.restful.soccer_league.domains.team.entity.Coach_;
import org.restful.soccer_league.domains.team.entity.Player;
import org.restful.soccer_league.domains.team.entity.Player_;
import org.restful.soccer_league.domains.team.entity.Team;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class PersonSpecification {

    public static Specification<Player> searchForPlayerInTheTeam(Long idTeam) {
        return (root, query, cb) -> {

            final Join<Team, Player> team = root.join(Player_.TEAM, JoinType.LEFT);

            return cb.equal(team.get("id"), idTeam);
        };

    }

    public static Specification<Coach> searchForCoachInTheTeam(Long idTeam) {
        return (root, query, cb) -> {
            final Join<Team, Coach> team = root.join(Coach_.TEAM, JoinType.LEFT);

            return cb.equal(team.get("id"), idTeam);
        };
    }

}

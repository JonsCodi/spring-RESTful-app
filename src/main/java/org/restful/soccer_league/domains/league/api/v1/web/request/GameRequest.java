package org.restful.soccer_league.domains.league.api.v1.web.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GameRequest implements Serializable {

    private static final long serialVersionUID = -121111212345531L;

    private String location;
    private String teamA;
    private String teamB;
    private int scoreTeamA;
    private int scoreTeamB;

}

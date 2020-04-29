package org.restful.soccer_league.domains.league.api.v1.web.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SoccerLeagueRequest implements Serializable {

    private static final long serialVersionUID = -12001213212345531L;

    private String name;

    private Set<String> teams;
    
}

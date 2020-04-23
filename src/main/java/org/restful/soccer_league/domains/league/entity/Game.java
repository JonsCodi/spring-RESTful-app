package org.restful.soccer_league.domains.league.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.restful.soccer_league.domains.team.entity.Team;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Game implements Serializable {

    private static final long serialVersionUID = -21234546855823972L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String location;

    @Column(name = "team_A")
    @NotNull
    private String teamA;

    @Column(name = "team_B")
    @NotNull
    private String teamB;

    @Column(name = "score_team_A")
    @NotNull
    private int scoreTeamA;

    @Column(name = "score_team_B")
    @NotNull
    private int scoreTeamB;

}

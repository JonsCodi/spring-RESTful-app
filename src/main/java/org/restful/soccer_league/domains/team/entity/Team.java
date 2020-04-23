package org.restful.soccer_league.domains.team.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Team implements Serializable {

    private static final long serialVersionUID = -213400959440323972L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @OneToOne(optional = false)
    @JoinColumn(name = "record_id", referencedColumnName = "id")
    @NotNull
    private Record record;

    @OneToMany(mappedBy = "team")
    @NotNull
    private Set<Player> players;

    @OneToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    @NotNull
    private Player captain;

    @OneToOne
    @JoinColumn(name = "coach_id", referencedColumnName = "id")
    @NotNull
    private Coach coach;

}

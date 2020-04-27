package org.restful.soccer_league.domains.team.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
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

    private String name;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "record_id", referencedColumnName = "id")
    private Record record;

    @OneToMany(mappedBy = "team")
    private Set<Player> players;

    @OneToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    private Player captain;

    @OneToOne
    @JoinColumn(name = "coach_id", referencedColumnName = "id")
    private Coach coach;

}

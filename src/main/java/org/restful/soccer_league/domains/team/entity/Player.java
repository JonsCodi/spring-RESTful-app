package org.restful.soccer_league.domains.team.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Player extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int number;

    private String position;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "team_id")
        private Team team;

    private boolean isCaptain;

    public Player(String name, String address, int number, String position, boolean isCaptain) {
        super(name, address);

        this.number = number;
        this.position = position;
        this.isCaptain = isCaptain;
    }

}

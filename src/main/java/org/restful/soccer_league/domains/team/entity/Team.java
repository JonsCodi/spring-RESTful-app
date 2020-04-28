package org.restful.soccer_league.domains.team.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
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

    @JsonIgnore
    @OneToMany(mappedBy = "team")
    private Set<Player> players;

    private String captain;

    @OneToOne
    @JoinColumn(name = "coach_id", referencedColumnName = "id")
    private Coach coach;

}

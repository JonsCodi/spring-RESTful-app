package org.restful.soccer_league.domains.team.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.restful.soccer_league.domains.team.enums.AccrLevel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Coach extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accrLevel;

    private int experience;

    @JsonIgnore
    @OneToOne(mappedBy = "coach")
    private Team team;

    public Coach(String name, String address, String accrLevel, int experience) {
        setName(name);
        setAddress(address);

        this.accrLevel = accrLevel;
        this.experience = experience;
    }

}

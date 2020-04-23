package org.restful.soccer_league.domains.team.entity;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class Person implements Serializable {

    private static final long serialVersionUID = 2408301959440323972L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

}

package org.restful.soccer_league.domains.team.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.restful.soccer_league.domains.league.entity.SoccerLeague;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
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

    @NotBlank
    private String name;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "record_id", referencedColumnName = "id")
    private Record record = new Record();

    @JsonIgnore
    @OneToMany(mappedBy = "team")
    private Set<Player> players = new HashSet<>();

    private String captain;

    @JsonIgnore
    @OneToOne(mappedBy = "team")
    private Coach coach;

    @ManyToMany(mappedBy = "teams")
    private Set<SoccerLeague> soccerLeagues = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "DATETIME")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "DATETIME")
    private LocalDateTime updatedAt;

    @Column(name = "disabled_at", columnDefinition = "DATETIME")
    private LocalDateTime disabledAt;

    public  Team(String name) {
        this.name = name;
    }

}

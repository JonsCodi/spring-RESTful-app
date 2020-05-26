package org.restful.soccer_league.domains.team.api.v1.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.restful.soccer_league.domains.team.enums.PersonType;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "persons", itemRelation = "person")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "personType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PlayerModel.class, name = "PLAYER"),
        @JsonSubTypes.Type(value = CoachModel.class, name = "COACH")
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class PersonModel extends RepresentationModel<PersonModel> implements Serializable {

    private static final long serialVersionUID = -240111221321232L;

    private Long id;
    private String name;
    private String address;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime disabledAt;

    private PersonType personType;

}

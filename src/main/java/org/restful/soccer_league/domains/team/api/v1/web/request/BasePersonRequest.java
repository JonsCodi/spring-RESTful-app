package org.restful.soccer_league.domains.team.api.v1.web.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.restful.soccer_league.domains.team.enums.PersonType;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "personType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PlayerCreateRequest.class, name = "PLAYER"),
        @JsonSubTypes.Type(value = CoachCreateRequest.class, name = "COACH")
})
public class BasePersonRequest implements Serializable {

    private static final long serialVersionUID = -3213213212345531L;

    private String name;

    private String address;

    private PersonType personType;

}

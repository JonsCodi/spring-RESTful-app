package org.restful.soccer_league.domains.team.api.v1.web.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.restful.soccer_league.domains.team.enums.PersonTypeEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotNull
    private PersonTypeEnum personType;

}

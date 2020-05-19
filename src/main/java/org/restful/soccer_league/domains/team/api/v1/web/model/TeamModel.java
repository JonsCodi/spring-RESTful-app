package org.restful.soccer_league.domains.team.api.v1.web.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.restful.soccer_league.domains.team.entity.Record;
import org.restful.soccer_league.domains.utils.enums.FiltersEnum;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "teams", itemRelation = "team")
@JsonFilter("team_filter")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class TeamModel extends RepresentationModel<TeamModel> implements Serializable {

    private static final long serialVersionUID = -240111221321232L;

    private Long id;
    private String name;
    private Record record;
    private String captain;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime disabledAt;

}

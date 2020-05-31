package org.restful.soccer_league.domains.utils.exceptions.handler.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@AllArgsConstructor
public class DetailError implements Serializable {

    private String resource;
    private String field;
    private String message;
    private String code;

}

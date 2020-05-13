package org.restful.soccer_league.domains.utils.exceptions.handler.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class DetailError implements Serializable {

    private String field;
    private String message;
    private String code;

}

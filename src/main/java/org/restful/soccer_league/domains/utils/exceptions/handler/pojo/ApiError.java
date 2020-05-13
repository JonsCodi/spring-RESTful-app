package org.restful.soccer_league.domains.utils.exceptions.handler.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class ApiError implements Serializable {

    private long timestamp;
    private String date;
    private String status;
    private Object data;
    private Object details;

}

package org.restful.soccer_league.domains.utils.exceptions.handler.pojo;

import lombok.Getter;
import org.restful.soccer_league.domains.utils.enums.ClientResponseType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ClientResponse implements Serializable {

    private long timestamp;
    private String date;
    private String status;
    private Object data;
    private Object details;

    public ClientResponse(Object data, Object details) {
        this.timestamp = System.currentTimeMillis();
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        this.status = ClientResponseType.ERROR.getMessage();
        this.data = data;
        this.details = details;
    }

}

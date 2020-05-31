package org.restful.soccer_league.domains.utils.exceptions.handler.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.restful.soccer_league.domains.utils.enums.ClientResponseType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ClientErrorResponse implements Serializable {

    private long timestamp;
    private String date;
    private String status;
    private Object data;
    private Object details;

    public ClientErrorResponse(Object data, Object details) {
        this.timestamp = System.currentTimeMillis();
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.status = ClientResponseType.ERROR.getMessage();
        this.data = data;
        this.details = details;
    }

}

package org.restful.soccer_league.domains.utils.api.web.v1.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import org.restful.soccer_league.domains.utils.enums.ClientResponseType;
import org.springframework.hateoas.Links;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JsonPropertyOrder({ "timestamp", "date", "status", "fields", "data", "links", "page" })
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ResponseSuccessBody implements Serializable {

    private static final long serialVersionUID = -942132132139613575L;

    private long timestamp;
    private String date;
    private String status;
    private Object fields;
    private final Object data;
    private Links links;
    private Object page;

    public ResponseSuccessBody(Links links, Object data, Object fields, Object page) {
        this.timestamp = System.currentTimeMillis();
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.data = data;
        this.fields = fields;
        this.status = ClientResponseType.SUCCESS.getMessage();
        this.links = links;
        this.page = page;
    }

}

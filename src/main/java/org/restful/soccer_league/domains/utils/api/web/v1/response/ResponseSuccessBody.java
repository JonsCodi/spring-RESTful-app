package org.restful.soccer_league.domains.utils.api.web.v1.response;

import lombok.Getter;
import org.restful.soccer_league.domains.utils.enums.ClientResponseType;
import org.springframework.hateoas.Links;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Getter
public class ResponseSuccessBody implements Serializable {

    private static final long serialVersionUID = -942132132139613575L;

    private long timestamp;
    private String date;
    private String status;
    private Links links;
    private Collection data;
    private Object page;

    public ResponseSuccessBody(Links links, Collection data, Object page) {
        this.timestamp = System.currentTimeMillis();
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        this.status = ClientResponseType.SUCCESS.getMessage();
        this.links = links;
        this.data = data;
        this.page = page;
    }

}

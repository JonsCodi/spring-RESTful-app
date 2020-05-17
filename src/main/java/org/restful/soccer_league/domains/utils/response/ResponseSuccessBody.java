package org.restful.soccer_league.domains.utils.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
@AllArgsConstructor
public class ResponseSuccessBody<T> implements Serializable {

    private static final long serialVersionUID = -942132132139613575L;

    private List<LinkPage> links;
    private int count;
    private long total;
    private List<T> data;

}

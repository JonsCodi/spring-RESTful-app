package org.restful.soccer_league.domains.utils.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class LinkPage implements Serializable {

    private static final long serialVersionUID = -933311234515L;

    private String href;
    private String method;
    private String rel;

}

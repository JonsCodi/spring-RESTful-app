package org.restful.soccer_league.domains.utils.constants;

import org.springframework.http.MediaType;

public final class PatchMediaType {

    public static final String APPLICATION_JSON_PATCH_VALUE = "application/json-patch+json";

    public static final MediaType APPLICATION_JSON_PATCH;

    static {
        APPLICATION_JSON_PATCH = MediaType.valueOf(APPLICATION_JSON_PATCH_VALUE);
    }
}

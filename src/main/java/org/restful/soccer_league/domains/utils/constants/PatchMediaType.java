package org.restful.soccer_league.domains.utils.constants;

import org.springframework.http.MediaType;

public final class PatchMediaType {

    public static final String APPLICATION_JSON_PATCH_VALUE = "application/json-patch+json";
    public static final String APPLICATION_MERGE_VALUE = "application/merge-patch+json";

    public static final MediaType APPLICATION_JSON_PATCH;
    public static final MediaType APPLICATION_MERGE_PATCH;

    static {
        APPLICATION_JSON_PATCH = MediaType.valueOf(APPLICATION_JSON_PATCH_VALUE);
        APPLICATION_MERGE_PATCH = MediaType.valueOf(APPLICATION_MERGE_VALUE);
    }
}

package org.restful.soccer_league.domains.utils;

public final class RequestURIUtils {

    private static final String REGEX_URI_RESOURCE_ID_OR_UUID = "\\b([a-f0-9\\\\-]*)\\s*";
    private static final String REGEX_URI_VERSION_NUMBER = "([/]v[1-9][/])|([/][1-9])";

    public static String getResourceFromURI(String requestURI) {
        return requestURI.replaceAll(REGEX_URI_RESOURCE_ID_OR_UUID, "")
                .replaceAll(REGEX_URI_VERSION_NUMBER, "")
                .replace("//", ".")
                .replace("/", "");
    }

}

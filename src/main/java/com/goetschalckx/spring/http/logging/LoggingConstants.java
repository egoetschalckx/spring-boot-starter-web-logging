package com.goetschalckx.spring.http.logging;

public class LoggingConstants {

    public static final String LOGGING_PREFIX = "logging.web";
    public static final String LOGGING_ENABLED = "enabled";
    public static final String LOGGING_RESPONSE_BODY = "include-body";

    public static final String LOGGING_RESPONSE_BODY_KEY = "${" + LOGGING_PREFIX + "." + LOGGING_RESPONSE_BODY + ":false}";

    public static final String HTTP_URL = "http.url";
    public static final String HTTP_METHOD = "http.method";
    public static final String HTTP_RESPONSE_CODE = "http.status";
    public static final String HTTP_HEADER_PREFIX = "http.headers.";
    public static final String SPAN_ID = "span.id";
    public static final String SPAN_KIND = "span.kind";

}

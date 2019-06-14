package com.goetschalckx.spring.http.logging;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import static com.goetschalckx.spring.http.logging.LogArgUtils.addIfValuePresent;

public class LogEventContext {

    private final Map<String, String> args = new HashMap<>(8);
    private final boolean includeBody;
    private final String spanKind;
    private final String spanId;
    private final String method;
    private final String url;

    public LogEventContext(
            boolean includeBody,
            String spanKind,
            String spanId,
            String method,
            String url
    ) {
        this.includeBody = includeBody;
        this.spanKind = spanKind;
        this.spanId = spanId;
        this.method = method;
        this.url = url;

        addIfValuePresent(args, LoggingConstants.SPAN_KIND, spanKind);
        addIfValuePresent(args, LoggingConstants.SPAN_ID, spanId);
        addIfValuePresent(args, LoggingConstants.HTTP_METHOD, method);
        addIfValuePresent(args, LoggingConstants.HTTP_URL, url);
    }

    public boolean getIncludeBody() {
        return includeBody;
    }

    public String getSpanKind() {
        return spanKind;
    }

    public String getSpanId() {
        return spanId;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getArgs() {
        return args;
    }

    public void addArgIfPresent(String key, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return;
        }

        args.put(key, value);
    }

}

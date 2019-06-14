package com.goetschalckx.spring.http.logging.client;

import com.goetschalckx.spring.http.logging.LogArgUtils;
import com.goetschalckx.spring.http.logging.LogEventContext;
import com.goetschalckx.spring.http.logging.LoggingConstants;

import net.logstash.logback.marker.Markers;
import org.slf4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ClientLogger {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ClientLogger.class);

    public void logResponse(LogEventContext context, ClientHttpResponse response) {
        String statusCode = ClientLoggingUtils.getStatusCode(response);
        LogArgUtils.addIfValuePresent(context.getArgs(), LoggingConstants.HTTP_RESPONSE_CODE, statusCode);
        LogArgUtils.addHeaders(context.getArgs(), response.getHeaders());

        if (context.getIncludeBody()) {
            String responseBody;
            try {
                responseBody = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                log.error("IOException during copyToString");
                responseBody = "[unknown]";
            }

            log.debug(
                    Markers.appendEntries(context.getArgs()),
                    "Inbound Message\n"
                            + "----------------------------\n"
                            + "Span Kind: {}\n"
                            + "Span ID: {}\n"
                            + "Method: {}\n"
                            + "URL: {}\n"
                            + "Status Code: {}\n"
                            + "Headers: {}\n"
                            + "Payload: {}\n"
                            + "--------------------------------------",
                    context.getSpanKind(),
                    context.getSpanId(),
                    context.getMethod(),
                    context.getUrl(),
                    statusCode,
                    response.getHeaders(),
                    responseBody);
        } else {
            log.debug(
                    Markers.appendEntries(context.getArgs()),
                    "Inbound Message\n"
                            + "--------------------------------------\n"
                            + "Span Kind: {}\n"
                            + "Span ID: {}\n"
                            + "Method: {}\n"
                            + "URL: {}\n"
                            + "Status Code: {}\n"
                            + "Headers: {}\n"
                            + "--------------------------------------",
                    context.getSpanKind(),
                    context.getSpanId(),
                    context.getMethod(),
                    context.getUrl(),
                    statusCode,
                    response.getHeaders());
        }
    }

    public void logRequest(LogEventContext context, HttpRequest request, byte[] body) {
        LogArgUtils.addHeaders(context.getArgs(), request.getHeaders());

        if (context.getIncludeBody()) {
            log.debug(
                    Markers.appendEntries(context.getArgs()),
                    "Outbound Message\n"
                            + "--------------------------------------\n"
                            + "Span Kind: {}\n"
                            + "Span ID: {}\n"
                            + "Method: {}\n"
                            + "URL: {}\n"
                            + "Headers: {}\n"
                            + "Payload: {}\n"
                            + "--------------------------------------",
                    context.getSpanKind(),
                    context.getSpanId(),
                    context.getMethod(),
                    context.getUrl(),
                    request.getHeaders(),
                    new String(body, StandardCharsets.UTF_8));
        } else {
            log.debug(
                    Markers.appendEntries(context.getArgs()),
                    "Outbound Message\n"
                            + "--------------------------------------\n"
                            + "Span Kind: {}\n"
                            + "Span ID: {}\n"
                            + "Method: {}\n"
                            + "URL: {}\n"
                            + "Headers: {}\n"
                            + "--------------------------------------",
                    context.getSpanKind(),
                    context.getSpanId(),
                    context.getMethod(),
                    context.getUrl(),
                    request.getHeaders());
        }
    }

}

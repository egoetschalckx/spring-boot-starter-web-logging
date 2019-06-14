package com.goetschalckx.spring.http.logging.client;

import com.goetschalckx.spring.http.logging.LogEventContext;
import com.goetschalckx.spring.http.logging.LoggingConstants;
import com.goetschalckx.spring.http.logging.SpanType;
import com.goetschalckx.spring.http.logging.span.SpanIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class ClientHttpRequestLoggingInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger log = LoggerFactory.getLogger(ClientHttpRequestLoggingInterceptor.class);
    private static final String SPAN_KIND = SpanType.CLIENT.logValue();

    private final boolean includeBody;
    private final ClientLogger clientLogger;
    private final SpanIdGenerator spanIdGenerator;

    public ClientHttpRequestLoggingInterceptor(
            boolean includeBody,
            ClientLogger clientLogger,
            SpanIdGenerator spanIdGenerator
    ) {
        this.includeBody = includeBody;
        this.clientLogger = clientLogger;
        this.spanIdGenerator = spanIdGenerator;
    }

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution
    ) throws IOException {

        // TODO: [eg] i really hate these... is there a better way? what if its not debug?
        if (!log.isDebugEnabled()) {
            return execution.execute(request, body);
        }

        String spanId = spanIdGenerator.spanId();
        MDC.put(LoggingConstants.SPAN_ID, spanId);
        MDC.put(SPAN_KIND, SPAN_KIND);

        try {
            return log(request, body, execution, spanId);
        } finally {
            MDC.remove(LoggingConstants.SPAN_ID);
            MDC.remove(SPAN_KIND);
        }
    }

    private ClientHttpResponse log(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution,
            String spanId
    ) throws IOException {
        String method = request.getMethod().name();
        String url = request.getURI().toString();

        LogEventContext context = new LogEventContext(
                includeBody,
                SPAN_KIND,
                spanId,
                method,
                url);

        clientLogger.logRequest(context, request, body);

        final ClientHttpResponse response = execution.execute(request, body);

        // i wonder if we might have the same shared memory problem here as in server logging
        clientLogger.logResponse(context, response);

        return response;
    }

}

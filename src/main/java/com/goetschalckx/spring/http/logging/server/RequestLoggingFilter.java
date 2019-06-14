package com.goetschalckx.spring.http.logging.server;

import com.goetschalckx.spring.http.logging.LogEventContext;
import com.goetschalckx.spring.http.logging.LoggingConstants;
import com.goetschalckx.spring.http.logging.SpanType;
import com.goetschalckx.spring.http.logging.span.SpanIdGenerator;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final String SPAN_KIND = SpanType.SERVER.logValue();

    private final boolean includeBody;
    private final ServerLogger serverLogger;
    private final SpanIdGenerator spanIdGenerator;

    public RequestLoggingFilter(
            boolean includeBody,
            ServerLogger serverLogger,
            SpanIdGenerator spanIdGenerator
    ) {
        this.includeBody = includeBody;
        this.serverLogger = serverLogger;
        this.spanIdGenerator = spanIdGenerator;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String spanId = spanIdGenerator.spanId();
        MDC.put(LoggingConstants.SPAN_ID, spanId);
        MDC.put(SPAN_KIND, SPAN_KIND);

        try {
            doFilterInternalInternal(request, response, filterChain, spanId);
        } finally {
            MDC.remove(LoggingConstants.SPAN_ID);
            MDC.remove(SPAN_KIND);
        }
    }

    private void doFilterInternalInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain,
            String spanId
    ) throws IOException, ServletException {
        boolean isFirstRequest = !isAsyncDispatch(request);

        HttpServletRequest requestToUse = request;
        if (isFirstRequest && !(request instanceof MultiReadHttpServletRequestWrapper)) {
            requestToUse = new MultiReadHttpServletRequestWrapper(request);
        }

        HttpServletResponse responseToUse = response;
        if (!(response instanceof ContentCachingResponseWrapper)) {
            responseToUse = new ContentCachingResponseWrapper(response);
        }

        try {
            filterChain.doFilter(requestToUse, responseToUse);
        } finally {
            internalInternalFinally(spanId, requestToUse, responseToUse);
        }
    }

    private void internalInternalFinally(
            String spanId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (!isAsyncStarted(request)) {
            // TODO: [eg] i once saw the response code end up on the request when they shared the same context.args... so i split them... hmm...
            String method = request.getMethod();
            String url = request.getRequestURL().toString();

            LogEventContext requestContext = new LogEventContext(
                    includeBody,
                    SpanType.SERVER.logValue(),
                    spanId,
                    method,
                    url);

            requestContext.getArgs().put("rnd", Integer.toString(ThreadLocalRandom.current().nextInt(1, 5)));

            serverLogger.logRequest(requestContext, request);

            LogEventContext responseContext = new LogEventContext(
                    includeBody,
                    SpanType.SERVER.logValue(),
                    spanId,
                    method,
                    url);

            responseContext.getArgs().put("rnd", Integer.toString(ThreadLocalRandom.current().nextInt(1, 5)));
            serverLogger.logResponse(responseContext, response);
        }
    }

}

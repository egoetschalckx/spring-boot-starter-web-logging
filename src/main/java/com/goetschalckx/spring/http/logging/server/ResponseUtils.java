package com.goetschalckx.spring.http.logging.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

public class ResponseUtils {

    private static final Logger log = LoggerFactory.getLogger(ResponseUtils.class);

    public static HttpHeaders getResponseHeaders(ContentCachingResponseWrapper responseWrapper) {
        Collection<String> headerNames = responseWrapper.getHeaderNames();
        HttpHeaders headers = new HttpHeaders();

        headerNames.forEach(
                x -> {
                    Collection<String> values = responseWrapper.getHeaders(x);
                    values.forEach(y -> headers.add(x, y));
                }
        );

        return headers;
    }

    public static HttpHeaders getResponseHeaders(HttpServletResponse response) {
        Collection<String> headerNames = response.getHeaderNames();
        HttpHeaders headers = new HttpHeaders();

        headerNames.forEach(
                x -> {
                    Collection<String> values = response.getHeaders(x);
                    values.forEach(y -> headers.add(x, y));
                }
        );

        return headers;
    }

    public static ContentCachingResponseWrapper getResponseWrapper(HttpServletResponse response) {
        ContentCachingResponseWrapper responseWrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (responseWrapper != null) {
            try {
                responseWrapper.copyBodyToResponse();
            } catch (IOException e) {
                log.error("IOException during copyBodyToResponse()", e);
            }
        }

        return responseWrapper;
    }

    public static String getResponseBody(HttpServletResponse response) {
        String responseBody = null;

        ContentCachingResponseWrapper responseWrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);

        if (responseWrapper != null) {
            byte[] buf = responseWrapper.getContentAsByteArray();

            try {
                responseWrapper.copyBodyToResponse();
            } catch (IOException e) {
                log.error("IOException during copyBodyToResponse()", e);
            }

            if (buf.length > 0) {
                String payload;
                try {
                    payload = new String(buf, 0, buf.length, responseWrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException ex) {
                    payload = "[unknown]";
                }

                responseBody = payload;
            }
        }

        return responseBody;
    }

}

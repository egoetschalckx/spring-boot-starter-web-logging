package com.goetschalckx.spring.http.logging.client;

import org.slf4j.Logger;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class ClientLoggingUtils {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ClientLoggingUtils.class);

    public static String getStatusCode(ClientHttpResponse response) {
        try {
            return Integer.toString(response.getStatusCode().value());
        } catch (IOException e) {
            log.error("IOException during response.getStatusCode()", e);
            return "[unknown]";
        }
    }

}

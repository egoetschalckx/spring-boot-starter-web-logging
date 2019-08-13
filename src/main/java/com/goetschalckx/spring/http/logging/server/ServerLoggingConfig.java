package com.goetschalckx.spring.http.logging.server;

import com.goetschalckx.spring.http.logging.LoggingConstants;
import com.goetschalckx.spring.http.logging.span.SpanIdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        prefix = LoggingConstants.LOGGING_PREFIX,
        name = LoggingConstants.LOGGING_ENABLED,
        havingValue = "true")
public class ServerLoggingConfig {

    private static final String LOGGING_RESPONSE_BODY_KEY = "${" + LoggingConstants.LOGGING_PREFIX + "." + LoggingConstants.LOGGING_RESPONSE_BODY + ":false}";

    // TODO: make this @Value a ConfigurationProperty instead
    @Value(LOGGING_RESPONSE_BODY_KEY)
    private boolean includeBody = false;

    @Bean
    @ConditionalOnMissingBean
    public RequestLoggingFilter requestLoggingFilter(
            ServerLogger serverLogger,
            SpanIdGenerator spanIdGenerator
    ) {
        return new RequestLoggingFilter(
                includeBody,
                serverLogger,
                spanIdGenerator);
    }

    @Bean
    @ConditionalOnMissingBean
    public ServerLogger serverLogger() {
        return new ServerLogger();
    }

}

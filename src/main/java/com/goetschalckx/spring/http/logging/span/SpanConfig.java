package com.goetschalckx.spring.http.logging.span;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpanConfig {

    @Bean
    public SpanIdGenerator spanIdGenerator() {
        return new SpanIdGenerator();
    }

}

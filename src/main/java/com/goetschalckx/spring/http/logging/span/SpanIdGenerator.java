package com.goetschalckx.spring.http.logging.span;

import java.util.UUID;

public class SpanIdGenerator {

    public String spanId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}

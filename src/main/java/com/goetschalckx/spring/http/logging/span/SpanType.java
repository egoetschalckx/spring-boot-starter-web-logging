package com.goetschalckx.spring.http.logging.span;

import java.util.Locale;

public enum SpanType {
    CLIENT,
    SERVER;

    SpanType() {
    }

    public String logValue() {
        return this.name().toLowerCase(Locale.getDefault());
    }
}

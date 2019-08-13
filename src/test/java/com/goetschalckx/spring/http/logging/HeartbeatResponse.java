package com.goetschalckx.spring.http.logging;

import java.time.Instant;

public class HeartbeatResponse {

    public Instant time;

    // thanks jackson. its either do this, or bring all of lombok in
    public HeartbeatResponse() { }

    public HeartbeatResponse(Instant time) {
        this.time = time;
    }

}

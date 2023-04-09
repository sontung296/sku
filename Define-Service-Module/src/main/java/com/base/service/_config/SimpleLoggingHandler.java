package com.base.service._config;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class SimpleLoggingHandler implements ObservationHandler<Observation.Context> {

    private static final Logger log = LoggerFactory.getLogger(SimpleLoggingHandler.class);

    @Override
    public boolean supportsContext(Observation.Context context) {
        return true;
    }

    @Override
    public void onStart(Observation.Context context) {
        log.info("Starting");
    }

    @Override
    public void onScopeOpened(Observation.Context context) {
        log.info("Scope opened");
    }

    @Override
    public void onScopeClosed(Observation.Context context) {
        log.info("Scope closed");
    }

    @Override
    public void onStop(Observation.Context context) {
        log.info("Stopping");
    }

    @Override
    public void onError(Observation.Context context) {
        log.info("Error");
    }
}

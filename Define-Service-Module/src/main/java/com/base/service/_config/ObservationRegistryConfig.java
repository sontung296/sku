package com.base.service._config;

import io.micrometer.core.instrument.Measurement;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Statistic;
import io.micrometer.core.instrument.observation.DefaultMeterObservationHandler;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.ObservationTextPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.StreamSupport;
@Slf4j
@Configuration
public class ObservationRegistryConfig {
    @Bean
    public ObservationRegistry observationRegistry(){
        ObservationRegistry observationRegistry = ObservationRegistry.create();
        MeterRegistry meterRegistry = new SimpleMeterRegistry();
        meterRegistry.getMeters().stream()
                .filter(m -> "sample".equals(m.getId().getName()))
                .flatMap(m -> StreamSupport.stream(m.measure().spliterator(), false))
                .filter(ms -> ms.getStatistic() == Statistic.MAX)
                .findFirst()
                .map(Measurement::getValue);

        observationRegistry
                .observationConfig()
                .observationHandler(new SimpleLoggingHandler())
                .observationHandler(new ObservationTextPublisher(log::info))
                .observationHandler(new DefaultMeterObservationHandler(meterRegistry));
        return observationRegistry;
    }
}

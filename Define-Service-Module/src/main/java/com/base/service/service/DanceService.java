package com.base.service.service;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class DanceService {
    private final ObservationRegistry observationRegistry;
    public String dancingWithObservability(String song) {
        return Observation
                .createNotStarted("dancingService", observationRegistry)
                .observe(() -> dancing(song));
    }

    public String dancing(String song) {
        if(song.equals("")){
            throw new IllegalArgumentException("Invalid input");
        }
        return song;
    }
}

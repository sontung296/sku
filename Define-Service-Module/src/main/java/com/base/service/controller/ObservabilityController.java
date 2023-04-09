package com.base.service.controller;

import com.base.service.service.DanceService;
import com.base.service.service.HelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v3")
@RequiredArgsConstructor
public class ObservabilityController {
    private final HelloService helloService;
    private final DanceService danceService;
//  Just use annotation to trace
    @GetMapping("/hello-service")
    public String helloService(@RequestParam("greeting") String greeting){
        return helloService.sayHello(greeting);
    }
//  Use config Bean to trace
    @GetMapping("/dance-service")
    public String danceService(@RequestParam("song") String song){
        return danceService.dancingWithObservability(song);
    }
}

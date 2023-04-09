package com.base.service.service;

import io.micrometer.observation.annotation.Observed;
import org.springframework.stereotype.Service;

@Observed(name = "greetingService")
@Service
public class HelloService {
    public String sayHello(String greeting) {
        if(greeting.equals("")){
            throw new IllegalArgumentException("Invalid input");
        }
        return greeting;
    }
}

package com.base.service.controller;

import com.base.service.controller.client.ClientServerTwo;
import com.base.service.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v2")
@RequiredArgsConstructor
public class ServerTwoController {
    private final ClientServerTwo clientServerTwo;
    @GetMapping(value = "/hi", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> hello(
            @RequestHeader("authorization") String authorization,
            @RequestParam("language")  String language) {
        return clientServerTwo.sayHello(authorization, language);
    }
    @GetMapping("/anything")
    public Mono<Book> anything(@RequestParam String something) {
        return clientServerTwo.saySomething(something, new Book(1L, "Name", "Author", 1999 ));
    }
    @GetMapping("/file")
    public Mono<byte[]> file() {
        return clientServerTwo.getMultiPart();
    }
}

package com.base.service.controller;


import com.base.service.client.ClientServerOne;
import com.base.service.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ServerOneController {
    private final ClientServerOne clientServerOne;
    @GetMapping("/hi")
    public Mono<String> hello() {
        return clientServerOne.sayHello();
    }
    @GetMapping("/anything")
    public Mono<Book> anything(@RequestParam String something) {
        return clientServerOne.saySomething(something, new Book(1L, "Name", "Author", 1999 ));
    }
    @GetMapping("/file")
    public Mono<byte[]> file() {
        return clientServerOne.getMultiPart();
    }
}

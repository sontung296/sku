package com.base.service.client;

import com.base.service.model.Book;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

@HttpExchange(value = "/api/v1", accept = MediaType.ALL_VALUE)
public interface ClientServerOne {
    @GetExchange("/hello")
    Mono<String> sayHello();
    @PostExchange(value = "/something", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<Book> saySomething(
            @RequestParam String something,
            @RequestBody Book book
    );
    @GetExchange("/multi-part")
    Mono<byte[]> getMultiPart();

}

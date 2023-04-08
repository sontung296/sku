package com.base.service.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientExceptionHandler {

    public static Mono<? extends Throwable> handle(ClientResponse clientResponse) {
        return Mono.deferContextual(Mono::just)
                .map(clientEndpoint -> createException( clientResponse));
    }
    private static ApplicationBaseException createException(ClientResponse response) {
        return new ApplicationBaseException(response.statusCode().value(), "Some detail message you can define here!", "Some message you can define here!");
    }
}

package com.base.service._config;


import com.base.service.controller.client.ClientServerOne;
import com.base.service.controller.client.ClientServerTwo;
import com.base.service.exception.ClientExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ApiConfig {

    private final String clientServerOneBaseUrl;
    private final String clientServerTwoBaseUrl;

    public ApiConfig(@Value("${client.server-one.base-url}") String clientServerOneBaseUrl,
                     @Value("${client.server-two.base-url}") String clientServerTwoBaseUrl
    ) {
        this.clientServerOneBaseUrl = clientServerOneBaseUrl;
        this.clientServerTwoBaseUrl = clientServerTwoBaseUrl;
    }

    @Bean
    public ClientServerOne clientServerOne() {
        WebClient webClient = WebClient.builder()
                .defaultStatusHandler(HttpStatusCode::isError, ClientExceptionHandler::handle)
                .baseUrl(clientServerOneBaseUrl)
                .build();
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(webClient))
                .build();
        return httpServiceProxyFactory.createClient(ClientServerOne.class);
    }

    @Bean
    public ClientServerTwo clientServerTwo() {
        WebClient webClient = WebClient.builder()
                .defaultStatusHandler(HttpStatusCode::isError, ClientExceptionHandler::handle)
                .baseUrl(clientServerTwoBaseUrl)
                .build();
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(webClient))
                .build();
        return httpServiceProxyFactory.createClient(ClientServerTwo.class);
    }

}

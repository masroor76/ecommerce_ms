package com.baloch.api_gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter implements GlobalFilter, Ordered {
    private final WebClient.Builder webClient;

    public AuthFilter(WebClient.Builder webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("Before Auth");

//        Object obj = webClient.build().get()
//                .uri("http://localhost:8081/api/v1/product")
//                .retrieve()
//                .bodyToMono(Object.class);
//        System.out.println(obj.toString());

        return chain.filter(exchange)
                .then(Mono.fromRunnable(()->{
                    System.out.println("After Auth");
                }));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}

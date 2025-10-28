package com.baloch.api_gateway.config;

import com.baloch.api_gateway.dto.User.User;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GatewayAuthFilter implements GlobalFilter, Ordered {

    private final ApplicationConfig applicationConfig;

    public GatewayAuthFilter(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getURI().getPath();

        if (path.startsWith("/api/v1/auth")) {
            return chain.filter(exchange);
        }

        String authorizationHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        String jwtToken = authorizationHeader.substring(7);

        try{
            return applicationConfig.webClient().get()
                    .uri("http://localhost:8084/api/v1/auth/validate-token")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer "+jwtToken)
                    .retrieve()
                    .toBodilessEntity()
                    .flatMap(newResponse -> {
                        if(newResponse.getStatusCode().is2xxSuccessful()){
                            return chain.filter(exchange);
                       }else {
                           exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                           return exchange.getResponse().setComplete();
                       }
                    });
        }catch (Exception e){
            System.out.println(e.toString());

            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
    }
    
    @Override
    public int getOrder() {
        return -1;
    }
}

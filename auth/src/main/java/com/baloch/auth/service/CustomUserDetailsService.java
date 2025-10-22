package com.baloch.auth.service;

import com.baloch.auth.model.User;
import com.baloch.auth.model.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CustomUserDetailsService implements UserDetailsService {
//    private final RestClient restClient;
//
//    public CustomUserDetailsService(RestClient.Builder restClientBuilder) {
//        this.restClient = restClientBuilder.baseUrl("http://localhost:8082").build();
//    }

//    public User getSingleDataFromAnotherService(String username) {
//        return restClient.get()
//                .uri("/api/v1/user/username/{username}", username)
//                .retrieve()
//                .body(User.class); // MyApiResponse is your data model
//    }


    private final WebClient webClient;

    public CustomUserDetailsService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8082").build();
    }


    public Mono<User> getSingleDataFromAnotherService(String username) {
        System.out.println(username);
        return webClient.get()
                .uri("/api/v1/user/username/{username}", username)
                .retrieve()
                .bodyToMono(User.class);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       User user = getSingleDataFromAnotherService(username).block();

        if(user==null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("User Not Found");
        }

        return new UserPrincipal(user);
    }
}

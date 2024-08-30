package com.example.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class GitHubWebClientService {
    private final WebClient webClient;

    public GitHubWebClientService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.github.com").build();
    }

    public Mono<List<Map<String, Object>>> getGitHubUserRepos(String accessToken) {
        return webClient.get()
                        .uri("/user/repos")
                        .headers(headers -> headers.setBearerAuth(accessToken)) 
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {});
    }

    public Mono<List<Map<String, Object>>> getUserFollowers(String accessToken) {
        return webClient.get()
                        .uri("/user/followers")
                        .headers(headers -> headers.setBearerAuth(accessToken))
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {});
    }
    
    public Mono<Map<String, Object>> getGitHubUserInfo(String accessToken) {
        return webClient.get()
                        .uri("/user")  
                        .headers(headers -> headers.setBearerAuth(accessToken))
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});
    }
}

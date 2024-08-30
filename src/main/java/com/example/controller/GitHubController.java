package com.example.controller;

import com.example.service.GitHubWebClientService;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class GitHubController {
    private final GitHubWebClientService gitHubService;
    private final OAuth2AuthorizedClientService authorizedClientService;

    public GitHubController(GitHubWebClientService gitHubService, OAuth2AuthorizedClientService authorizedClientService) {
        this.gitHubService = gitHubService;
        this.authorizedClientService = authorizedClientService;
    }

    @GetMapping("/userInfo")
    public String getUserInfo(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());

        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        String tokenValue = accessToken.getTokenValue();

        List<Map<String, Object>> repos = gitHubService.getGitHubUserRepos(tokenValue).block();
        List<Map<String, Object>> followers = gitHubService.getUserFollowers(tokenValue).block();
        Map<String, Object> userInfo = gitHubService.getGitHubUserInfo(tokenValue).block();

        model.addAttribute("repos", repos);
        model.addAttribute("followers", followers);
        model.addAttribute("userInfo", userInfo);

        return "userInfo"; 
    }
}

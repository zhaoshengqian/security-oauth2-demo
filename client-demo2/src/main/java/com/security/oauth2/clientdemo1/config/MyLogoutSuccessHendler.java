package com.security.oauth2.clientdemo1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class MyLogoutSuccessHendler implements LogoutSuccessHandler {

    public static String logoutUrl = "http://127.0.00.1:8082/resource/logout";

    @Autowired
    OAuth2AuthorizedClientService auth2AuthorizedClientService;


    @Autowired
    RestTemplate restTemplate;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken)authentication;

        OAuth2AuthorizedClient oAuth2AuthorizedClient = auth2AuthorizedClientService.loadAuthorizedClient(authenticationToken.getAuthorizedClientRegistrationId(), authenticationToken.getName());

        String token = oAuth2AuthorizedClient.getAccessToken().getTokenValue();

        HttpHeaders headers =  new HttpHeaders();
        headers.set("Authorization",token);

        HttpEntity<String> requestEntity = new HttpEntity<String>(null,headers);
        //根据AccessToken 请求资源信息
        ResponseEntity<String> result = restTemplate.exchange(logoutUrl, HttpMethod.GET,requestEntity,String.class);


    }
}

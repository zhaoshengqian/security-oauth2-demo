package com.security.oauth2.clientdemo1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *  使用 RestTemplate 携带的 access_token 请求资源服务器，获取信息
 */

@RestController
public class ResourceEndpointController {

        private static  final String URL_GET_USER_PHONE = "http://127.0.0.1:8082/resource/phone";

        @Autowired
        OAuth2AuthorizedClientService auth2AuthorizedClientService;

        @Autowired
         RestTemplate restTemplate;


    /**
     * 根据 accessToken 获取  资源信息
     * @param authentication
     * @return
     */
    @GetMapping("/phone")
    public String userinfo(OAuth2AuthenticationToken authentication){

            OAuth2AuthorizedClient  auth2AuthorizedClient  = auth2AuthorizedClientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(),authentication.getName());

            HttpHeaders  headers =  new HttpHeaders();
            headers.set("Authorization","Bearer "+auth2AuthorizedClient.getAccessToken().getTokenValue());

            HttpEntity<String> requestEntity = new HttpEntity<String>(null,headers);

            //根据AccessToken 请求资源信息
            ResponseEntity<String> response = restTemplate.exchange(URL_GET_USER_PHONE, HttpMethod.GET,requestEntity,String.class);

            return response.getBody();
        }


    /**
     * 获取 access_token
     */

    @GetMapping("/getToken")
    public String  getAccessToken(OAuth2AuthenticationToken authentication){


        OAuth2AuthorizedClient  auth2AuthorizedClient  = auth2AuthorizedClientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(),authentication.getName());

        return  auth2AuthorizedClient.getAccessToken().getTokenValue();

    }

    @GetMapping("/getResource")
    public Object getResource(OAuth2AuthenticationToken authentication){
        OAuth2AuthorizedClient  auth2AuthorizedClient  = auth2AuthorizedClientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(),authentication.getName());

        HttpHeaders  headers =  new HttpHeaders();
        headers.set("Authorization","Bearer "+auth2AuthorizedClient.getAccessToken().getTokenValue());

        HttpEntity<String> requestEntity = new HttpEntity<String>(null,headers);

        //根据AccessToken 请求资源信息
        ResponseEntity<String> response = restTemplate.exchange("http://127.0.0.1:8082/resource/resource", HttpMethod.GET,requestEntity,String.class);

        return response.getBody();
    }
    @GetMapping("/getResource2")
    public Object getResource2(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient  auth2AuthorizedClient1,OAuth2AuthenticationToken authentication){
        HttpHeaders  headers =  new HttpHeaders();
        headers.set("Authorization","Bearer "+auth2AuthorizedClient1.getAccessToken().getTokenValue());

        HttpEntity<String> requestEntity = new HttpEntity<String>(null,headers);

        //根据AccessToken 请求资源信息
        ResponseEntity<String> response = restTemplate.exchange("http://127.0.00.1:8082/resource/resource", HttpMethod.GET,requestEntity,String.class);

        return response.getBody();
    }
    
}

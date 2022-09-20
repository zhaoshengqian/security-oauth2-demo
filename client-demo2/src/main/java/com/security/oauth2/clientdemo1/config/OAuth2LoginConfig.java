//package com.security.oauth2.clientdemo1.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
//import org.springframework.security.oauth2.core.AuthorizationGrantType;
//import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
//
//@Configuration
//public class OAuth2LoginConfig {
//
//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository() {
//        return new InMemoryClientRegistrationRepository(aaaClientRegistration());
//    }
//
//    @Value("${spring.security.oauth2.client.registration.my-client.client-id}")
//    private String clientId;
//
//    @Value("${spring.security.oauth2.client.registration.my-client.client-secret}")
//    private String clientSecret;
//
//    private ClientRegistration aaaClientRegistration() {
//        //指定服务的名称，例子中，是 aaa。这是个ID，名字随便取。主要用于区分环境中存在种OAuth2服务。
//        return ClientRegistration.withRegistrationId("my-client")
//                //clientId是服务分配的一个client_id，在OAuth2服务那里注册Application时分配
//                .clientId(clientId)
//                //clientSecret与clientId一样，由服务分配。这个例子里，使用了Springboot配置的值，
//                // 主要目的是为了区分开发环境和生产环境。可以在服务那里注册2个Application，一个用于开发，
//                // 一个用于上线。由于每个Application的clientId和clientSecret都不一样，
//                // 所以使用从配置中读取的方式。
//                .clientSecret(clientSecret)
//                //服务端支持的认证方式
//                .clientAuthenticationMethod(ClientAuthenticationMethod.POST)
//                //服务端认证通过后，回调到本地的URL。这里的{baseUrl}和{registrationId}是框架可以自己填的。
//                // {baseUrl}是本地应用的地址，如http://127.0.0.1。{registrationId}则是(1)中使用的ID。
//                // 在OAuth2服务那里注册Application时，需要填写这么一个回调地址。默认情况下，
//                // 这个地址的格式必需是/login/oauth2/code/。
//                // 所以，在这个例子中，在服务那里注册Application时，
//                // 填写的回调地址必需是http://127.0.0.1//login/oauth2/code/aaa。
////                .redirectUriTemplate("http://127.0.0.1:8083/client/login")
////                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
//                .redirectUri("http://127.0.0.1:8083/client/login")
//                //随便写
//                .clientName("client")
//                //服务提供者那里给出的参数，照着填就行
//                .tokenUri("http://127.0.0.1:8081/oauth/oauth/token")
//                //服务提供者那里给出的参数，照着填就行
//                .authorizationUri("http://127.0.0.1:8081/oauth/oauth/authorize")
//                //服务提供者那里给出的参数，照着填就行
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                //服务提供者那里给出的参数，照着填就行
//                .scope("ROLE_ADMIN,ROLE_USER,ROLE_API")
//                //userInfoUri返回的json中，标识username的key。
//                .userNameAttributeName("user_name")
//                //服务提供者那里给出的参数，照着填就行
//                .userInfoUri("http://127.0.0.1:8082/resource/resource")
//                //服务提供者那里给出的参数，照着填就行
//                .jwkSetUri("")
//                .build();
//    }
//}

package com.security.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class TokenConfig {

//    /**
//     * 令牌策略-内存方式
//     * @return
//     */
//    @Bean
//    public TokenStore tokenStore(){
//        //内存方式
//        return new InMemoryTokenStore();
//    }



//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;
//
//    /**
//     * 令牌策略-redis方式
//     * @return
//     */
//    @Bean
//    public TokenStore tokenStore(){
//        return new RedisTokenStore(redisConnectionFactory);
//    }


    //加密盐，防止被篡改
    private String SIGNING_KEY = "uaa123";
    /**
     * 令牌策略-jwt方式
     * @return
     */
    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //对称密匙，资源服务器使用该密匙验证
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }
}

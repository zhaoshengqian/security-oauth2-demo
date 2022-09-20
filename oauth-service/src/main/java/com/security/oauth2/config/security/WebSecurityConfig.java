package com.security.oauth2.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration //标志为一个配置文件
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
//        //密码规则为原始数据，不加密
//        return NoOpPasswordEncoder.getInstance();
        //密码规则为自定义md5规则
        return new MD5PasswordEncoder();
    }
    /**
     * 认证策略，核心配置，具体的权限控制规则配置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            //解决跨域问题
            .csrf().disable()
            .authorizeRequests()
            //设置r1路径的访问权限是 p1
//            .antMatchers("/r/r1").hasAuthority("p1")
//            //设置r2路径的访问权限是 p2
//            .antMatchers("/r/r2").hasAuthority("p2")
            //请求可以访问
                .antMatchers("/login*","/swagger**/**","/**/v3/api-docs").permitAll()
                .anyRequest().authenticated()
            .and()
            //开启表单登录，如果检测导没有登录会跳转到登录页
            .formLogin()
            //登录页面
            .loginPage("/login.html")
            //定义登录方法
            .loginProcessingUrl("/login");
//            //自定义登录成功的页面地址
//            .successForwardUrl("/login-success")
//            .and()
//            .sessionManagement()
//            //登陆时，如果需要就创建一个session
//            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);;
    }


    //认证管理器,oauth2需要，放入容器
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}

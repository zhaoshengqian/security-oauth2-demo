package com.security.oauth2.config.security;

import com.security.oauth2.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *  重写登录规则,并加入容器
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username:"+username);
        //从数据库查询
        Map<String,Object> dbUser = userDao.getUserByUsername(username);
        if (dbUser == null){
            return null;
        }
        //根据用户名查询用户权限
        String[] premiss = dbUser.get("auth").toString().split(",");
        UserDetails build = User
                //账号
                .withUsername(dbUser.get("username").toString())
                //密码
                .password(dbUser.get("password").toString())
                //权限
                .authorities(premiss).build();
        return build;
    }
}

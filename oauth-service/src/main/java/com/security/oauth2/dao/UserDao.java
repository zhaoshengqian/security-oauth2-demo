package com.security.oauth2.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 根据账号查询
     * @return
     */
    public Map<String,Object> getUserByUsername(String userName){
        String sql = "select * from t_user where username=?";
        Map<String, Object> user = jdbcTemplate.queryForMap(sql, new Object[]{userName});
        return user;
    }

}

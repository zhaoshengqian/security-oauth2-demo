package com.security.oauth2.config.security;

import com.security.oauth2.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MD5PasswordEncoder implements PasswordEncoder {

    private Logger logger = LoggerFactory.getLogger(MD5PasswordEncoder.class);

    @Override
    public String encode(CharSequence rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        } else {
            return MD5Util.md5Encrypt32Upper(rawPassword.toString());
        }
    }

    /**
     * 重写密码验证规则
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        } else if (encodedPassword != null && encodedPassword.length() != 0) {
            String s = encode(rawPassword);
            return s.equals(encodedPassword);
        } else {
            this.logger.warn("Empty encoded password");
            return false;
        }
    }
    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return false;
    }
}

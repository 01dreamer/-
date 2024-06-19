package com.hy.newsbackend.common.config;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class SecurityConfigTest {
    @Resource
    private PasswordEncoder passwordEncoder;
    // 校验密码对象
    @Test
    void passwordTest(){
        // 盐，是一个随机的盐
        String encode = passwordEncoder.encode("123");
        String encode2 = passwordEncoder.encode("123");
        System.out.println(encode);
        System.out.println(encode2);
        System.out.println(passwordEncoder.matches("123", "$2a$10$K/v.QouYmV576JniSNqfB.9Pr5Sv8.hncUVhqFDhvl/dVa64XucI2"));
        System.out.println(passwordEncoder.matches("123", "$2a$10$TDqUB6BccASWPGrVG3v1t.InxrkaXiXBBnziQn93HuvHCRb5as.YO"));
    }
}

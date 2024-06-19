package com.hy.newsbackend.common.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestJwtUtil {
    @Test
    void jwtTest(){
        //生成jwt
        String jwt = JwtUtil.createJWT("1000","10086", 60 * 60 * 1000L);
        System.out.println(jwt);
        System.out.println(JwtUtil.parseJWT(jwt));
    }
}

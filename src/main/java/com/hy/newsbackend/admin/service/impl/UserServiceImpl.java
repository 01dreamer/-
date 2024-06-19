package com.hy.newsbackend.admin.service.impl;

import com.alibaba.fastjson2.JSON;
import com.hy.newsbackend.admin.service.UserService;
import com.hy.newsbackend.common.utils.JwtUtil;
import com.hy.newsbackend.common.result.R;
import com.hy.newsbackend.pojo.entity.User;
import com.hy.newsbackend.pojo.vo.LoginUser;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public R login(User user) {
        // 1、封装Authentication对象
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        // 2、进行校验
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 如果认证不通过，就返回自定义的异常
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }
        // 如果认证成功，就从authenticate对象的getPrincipal方法中拿到认证通过后的登录用户对象
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
//        // 得到用户编号
//        String userid = loginUser.getUser().getId().toString();

        // 使用fastjson的方法，把对象转为字符串
        String loginUserString = JSON.toJSONString(loginUser);
        // 生成令牌
        String jwt = JwtUtil.createJWT(loginUserString,null);

        // 1、把登录用户存入redis

        // jwt的键名
        String tokenKey = "token_"+jwt;
        // 存储redis白名单
        stringRedisTemplate.opsForValue().set(tokenKey,jwt,JwtUtil.JWT_TTL/1000, TimeUnit.SECONDS);


        // 2、把jwt返回给调用者
        return R.token("token",jwt);

    }
}

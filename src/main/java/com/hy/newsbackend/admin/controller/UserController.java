package com.hy.newsbackend.admin.controller;

import com.hy.newsbackend.admin.service.UserService;
import com.hy.newsbackend.common.result.R;
import com.hy.newsbackend.pojo.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final StringRedisTemplate stringRedisTemplate;


    @PostMapping("login")
    public R login(@RequestBody User user){
        return userService.login(user);
    }
    @PostMapping("logout")
    public R logout(HttpServletRequest request, HttpServletResponse response){
        // 在logout中要获取jwt
        String token = request.getHeader("Authorization");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null){
            // 1、清除上下文
            new SecurityContextLogoutHandler().logout(request,response,authentication);
            // 2、清除redis
            // TODO
            stringRedisTemplate.delete("token_"+token);
        }
        return R.ok();
    }
}

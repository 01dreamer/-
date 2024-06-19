package com.hy.newsbackend.common.filters;

import com.alibaba.fastjson2.JSON;
import com.hy.newsbackend.common.exception.CustomerAuthenticationException;
import com.hy.newsbackend.common.handler.LoginFailureHandler;
import com.hy.newsbackend.common.utils.JwtUtil;
import com.hy.newsbackend.pojo.vo.LoginUser;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 * token验证过滤器
 * 每一个servlet请求，只会执行一次
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private LoginFailureHandler loginFailureHandler;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        // 1.判断是否是登录接口/user/login，是则直接放行
        try {
            // 获取当前请求的url地址
            String uri = request.getRequestURI();
            System.out.println(uri);
            if (uri.equals("/user/login")) {
                this.validateToken(request);
            }
        } catch (AuthenticationException e) {
            loginFailureHandler.onAuthenticationFailure(request,response,e);
        }
        // 5.放行
        filterChain.doFilter(request,response);

    }
    // 用于token校验的方法
    private void validateToken(HttpServletRequest request){
        // 获取token
        String token = request.getHeader("Authorization");
        // 2.判断是否带有token
        if (Objects.isNull(token)) {
            throw new CustomerAuthenticationException("token为空");
        }

        // redis进行校验
        String redisStr = stringRedisTemplate.opsForValue().get("token_" + token);
        if (ObjectUtils.isEmpty(redisStr)){
            throw new CustomerAuthenticationException("token已过期");
        }

        // 3.校验令牌
        Claims claims = JwtUtil.parseJWT(token);
        String loginUserStr = Objects.requireNonNull(claims).getSubject();
        LoginUser loginUser = JSON.parseObject(loginUserStr, LoginUser.class);
        System.out.println(loginUser);

        // 4.把验证完获取到的用户信息再次放入spring-security的上下文
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    }
}

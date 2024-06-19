package com.hy.newsbackend.common.config;

import com.hy.newsbackend.common.filters.JwtAuthenticationTokenFilter;
import com.hy.newsbackend.common.handler.AnonymousAuthenticationHandler;
import com.hy.newsbackend.common.handler.CustomerAccessDeniedHandler;
import com.hy.newsbackend.common.handler.LoginFailureHandler;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    // 自定义的用于认证的过滤器，进行jwt校验操作
    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    // 认证用户无权限访问的处理器
    @Resource
    private CustomerAccessDeniedHandler customerAccessDeniedHandler;

    // 客户端进行认证数据的提交时，或是匿名用户访问受限资源的处理器
    @Resource
    private AnonymousAuthenticationHandler anonymousAuthenticationHandler;

    // 用户认证校验失败处理器
    @Resource
    private LoginFailureHandler loginFailureHandler;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 登录时需要调用AuthenticationManager.authenticate执行一次校验
     * @param config 根据传入的AuthenticationConfiguration config
     * @return 调用getAuthenticationManager()进行校验
     * @throws Exception 抛出空指针异常
     */
    // authenticate
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    // 配置
    // 1、登录请求放行
    // 这是一个表示安全过滤链器的对象
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws Exception{
        // 配置关闭csrf机制
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        // 用户认证校验失败处理器
        httpSecurity.formLogin(httpSecurityFormLoginConfigurer ->
                httpSecurityFormLoginConfigurer
                        .failureHandler(loginFailureHandler));

        // STATELESS (无状态): 表示应用程序是无状态的，不会创建会话。
        httpSecurity.sessionManagement(httpSecuritySessionManagementConfigurer ->
                httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 配置请求拦截方式
        httpSecurity.authorizeHttpRequests(auth->
                // 登录请求放行
                auth.requestMatchers("/user/login","/oauth/**")
                .permitAll()
                .anyRequest()
                .authenticated());
        // 配置过滤器的执行顺序
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 添加自定义的异常处理
        httpSecurity.exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                httpSecurityExceptionHandlingConfigurer
                .accessDeniedHandler(customerAccessDeniedHandler)
                .authenticationEntryPoint(anonymousAuthenticationHandler));
        return httpSecurity.build();
        // 把token校验过滤器添加到过滤链中
    }

}

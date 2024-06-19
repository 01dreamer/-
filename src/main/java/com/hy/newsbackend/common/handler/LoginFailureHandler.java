package com.hy.newsbackend.common.handler;

import com.alibaba.fastjson2.JSON;
import com.hy.newsbackend.common.exception.CustomerAuthenticationException;
import com.hy.newsbackend.common.result.R;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 认证校验失败处理类
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        // 设置客户端响应的格式
        response.setContentType("application/json;charset=UTF-8");
        // 获取输出流
        ServletOutputStream outputStream = response.getOutputStream();
        String message ; // 提示信息
        int code = 500; // 错误编码

        // 判断异常类型
        if (exception instanceof AccountExpiredException) {
            message = "账户过期, 登录失败!";
        } else if (exception instanceof BadCredentialsException) {
            message = "用户名或密码错误, 登录失败!";
        } else if (exception instanceof CredentialsExpiredException) {
            message = "密码过期, 登录失败!";
        } else if (exception instanceof DisabledException) {
            message = "账户被禁用, 登录失败!";
        } else if (exception instanceof LockedException) {
            message = "账户被锁定, 登录失败!";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            message = "账户不存在, 登录失败!";
        } else if (exception instanceof CustomerAuthenticationException) {
            message = exception.getMessage();
            code = 600;
        } else {
            message = "登录失败!";
        }

        // 将错误信息转换成JSON
        String result = JSON.toJSONString(R.errorMsg(message));
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();

    }
}

package com.hy.newsbackend.common.handler;

import com.alibaba.fastjson2.JSON;
import com.hy.newsbackend.common.result.R;
import com.hy.newsbackend.common.result.ResponseStatusEnum;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 客户端进行认证数据的提交时，或是匿名用户访问受限资源的处理器
 */
@Component
public class AnonymousAuthenticationHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        // 设置客户端的响应内容类型
        response.setContentType("application/json;charset=UTF-8");

        // 处理异常返回
        String result = exceptionResult(authException);

        //测试
        System.out.println(authException.getMessage()+"=="+authException);

        //获取输出流
        ServletOutputStream outputStream = response.getOutputStream();

        outputStream.write(result.getBytes(StandardCharsets.UTF_8));

        //关闭输出流
        outputStream.flush();
        outputStream.close();

    }
    public String exceptionResult(AuthenticationException authException){
        if (authException instanceof BadCredentialsException){
            // 用户名未找到
            return JSON.toJSONString(R.errorCustom(ResponseStatusEnum.ADMIN_NAME_NULL_ERROR));
        } else if (authException instanceof InternalAuthenticationServiceException){
            // 用户名为空
            return JSON.toJSONString(R.errorCustom(ResponseStatusEnum.ADMIN_USERNAME_EXIST_ERROR));
        }
        // 其他身份异常处理
        return JSON.toJSONString(R.errorCustom(ResponseStatusEnum.ADMIN_CREATE_ERROR));
    }
}

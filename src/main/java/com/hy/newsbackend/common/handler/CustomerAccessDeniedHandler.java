package com.hy.newsbackend.common.handler;

import com.alibaba.fastjson2.JSON;
import com.hy.newsbackend.common.result.R;
import com.hy.newsbackend.common.result.ResponseStatusEnum;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 认证用户无权限访问的处理器
 */
@Component
public class CustomerAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        // 设置客户端的响应的内容类型
        response.setContentType("application/json;charset=UTF-8");
        // 获取输出流
        ServletOutputStream outputStream = response.getOutputStream();
        // 消除循环引用
        String result = JSON.toJSONString(R.errorCustom(ResponseStatusEnum.UNAUTHORIZED_ACCESS));
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}

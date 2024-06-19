package com.hy.newsbackend.admin.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.hy.newsbackend.pojo.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("oauth")
public class AuthController {
    /**
     *
     */
    @RequestMapping("render")
    public void renderAuth( HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest();
        String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
        response.sendRedirect(authorizeUrl);
    }
    /**
     *
     */
    @RequestMapping("callback")
    public Object login( AuthCallback callback) {
        AuthRequest authRequest = getAuthRequest();
        JSONObject jsonObject = JSONUtil.parseObj(authRequest.login(callback),false);
        JSONObject o = JSONUtil.parseObj(jsonObject.get("data"), false);
        User user = new User();
        user.setId(Long.parseLong(o.get("uuid").toString()));
        user.setUserName(o.get("username").toString());
        user.setNickName(o.get("nickname").toString());
        user.setAvatar(o.get("avatar").toString());
        return user;
    }

    private AuthRequest getAuthRequest() {
        // 创建授权request
        return new AuthGiteeRequest(AuthConfig.builder()
                .clientId("cc7071e6dd84e5d644a5cfa2235faa6714ff4bc8cebc27b429840cafee02117f")
                .clientSecret("d4a5a4a01230f932c51afc6fba02d491d36fdb4d44cc1f82774b953b84878e9c")
                .redirectUri("http://localhost:8080/oauth/callback")
                .build());

    }

}

package com.hy.newsbackend.pojo.vo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.hy.newsbackend.pojo.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {

    private User user;
    // 存储权限信息
    private List<String> permissions;

    public LoginUser(User user,List<String> permissions){
        this.user=user;
        this.permissions = permissions;
    }
    // 存储SpringSecurity所需要的权限信息的集合 中转操作
    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;

    // 用来返回权限信息
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities!=null){
            return authorities;
        }
        // 把permissions中的String类型的权限信息封装成SimpleGrantedAuthority对象
        authorities = this.permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;
    }

    // 获取密码
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 获取用户名
    @Override
    public String getUsername() {
        return user.getUserName();
    }
    // 判断账号是否过期
    @Override
    public boolean isAccountNonExpired(){
        return true;
    }
    // 判断账号是否锁定
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }
    // 判断账号是否没有超时
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    // 判断账号是否可用
    @Override
    public boolean isEnabled() {
        return true;
    }

}

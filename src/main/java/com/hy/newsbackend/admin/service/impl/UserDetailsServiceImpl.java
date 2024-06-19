package com.hy.newsbackend.admin.service.impl;

import com.hy.newsbackend.admin.repository.PermissionRepository;
import com.hy.newsbackend.admin.repository.UserRepository;
import com.hy.newsbackend.admin.service.PermissionService;
import com.hy.newsbackend.admin.service.RoleService;
import com.hy.newsbackend.pojo.entity.Permission;
import com.hy.newsbackend.pojo.entity.Role;
import com.hy.newsbackend.pojo.entity.User;
import com.hy.newsbackend.pojo.vo.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PermissionService permissionService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1、根据用户名查询账号信息
        // 查询用户信息
        // Optional<User> userOptional = userRepository.findByUserName(username);
        // User user = userOptional.orElseThrow(() -> new RuntimeException("用户名或密码错误"));
        User user = userRepository.findByUserName(username);
        // 2、赋权操作
        // TODO
        // 获取用户的角色
        List<Role> roles = roleService.findByUserId(user.getId());
        // 使用流处理每个角色，获取权限并提取perms字段，最后收集到一个列表中
        List<String> list = roles.stream()
                .flatMap(role -> permissionService.findByRoleId(role.getId()).stream())
                .map(Permission::getPerms)
                .collect(Collectors.toList());
        // 3、返回UserDetail对象
        return  new LoginUser(user,list);
    }
}

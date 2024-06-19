package com.hy.newsbackend.admin.service.impl;

import com.hy.newsbackend.admin.repository.RoleRepository;
import com.hy.newsbackend.admin.repository.UserRoleRepository;
import com.hy.newsbackend.admin.service.RoleService;
import com.hy.newsbackend.pojo.entity.Role;
import com.hy.newsbackend.pojo.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    @Override
    public List<Role> findByUserId(Long userId) {
        List<UserRole> userRoles = userRoleRepository.findAllByUserId(userId);
        return userRoles.stream().map(userRole -> roleRepository.getRoleById(userRole.getRoleId())).toList();
    }
}

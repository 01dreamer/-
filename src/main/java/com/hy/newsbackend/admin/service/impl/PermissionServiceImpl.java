package com.hy.newsbackend.admin.service.impl;

import com.hy.newsbackend.admin.repository.PermissionRepository;
import com.hy.newsbackend.admin.repository.RolePermissionRepository;
import com.hy.newsbackend.admin.service.PermissionService;
import com.hy.newsbackend.pojo.entity.Permission;
import com.hy.newsbackend.pojo.entity.RolePermission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final RolePermissionRepository rolePermissionRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public List<Permission> findByRoleId(Long roleId) {
        List<RolePermission> rolePermissions = rolePermissionRepository.findAllByRoleId(roleId);
        return rolePermissions.stream()
                .map(rolePermission -> permissionRepository.findById(rolePermission.getPermissionId()).orElse(null))
                .toList();
    }
}

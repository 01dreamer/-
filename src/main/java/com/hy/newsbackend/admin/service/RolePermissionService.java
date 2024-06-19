package com.hy.newsbackend.admin.service;



import com.hy.newsbackend.pojo.entity.RolePermission;

import java.util.List;

/**
 * RolePermissionService
 *
 * @author dhb
 */
public interface RolePermissionService {
    void saveBatch(List<RolePermission> permissionList);
    List<RolePermission> findAllByRoleId(Long roleId);
    void removeByRoleId(Long roleId);
    List<Long> getPermissionIdsByRoleId(Long roleId);
}

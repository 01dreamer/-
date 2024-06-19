package com.hy.newsbackend.admin.service;

import com.hy.newsbackend.pojo.entity.Permission;

import java.util.List;

public interface PermissionService{

    /**
     * 根据角色id查找所有权限
     * @param roleId 传入角色id
     * @return 返回权限数组
     */
    List<Permission> findByRoleId(Long roleId);
}

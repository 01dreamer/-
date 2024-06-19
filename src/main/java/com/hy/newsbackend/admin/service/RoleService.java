package com.hy.newsbackend.admin.service;

import com.hy.newsbackend.pojo.entity.Role;

import java.util.List;

public interface RoleService {
    /**
     * 根据用户id查找所有角色
     * @param userId 传入用户id
     * @return 返回角色数组
     */
    List<Role> findByUserId(Long userId);
}

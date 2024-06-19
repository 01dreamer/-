package com.hy.newsbackend.admin.service;



import com.hy.newsbackend.pojo.entity.UserRole;

import java.util.List;

/**
 * UserRoleService
 *
 * @author dhb
 */
public interface UserRoleService {
    void removeByRoleId(Long roleId);
    List<UserRole> findAllByRoleId(Long roleId);
    void saveBatch(List<UserRole> userRoleList);
    void removeUserRolesByRoleIdAndUserIds(Long roleId, List<Long> userIds);
}

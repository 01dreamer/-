package com.hy.newsbackend.admin.repository;


import com.hy.newsbackend.pojo.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * RolePermissionMapper
 *
 * @author dhb
 */
public interface RolePermissionRepository extends JpaRepository<RolePermission,Long> {
    List<Long> findPermissionIdsByRoleId(Long roleId);
    List<RolePermission> findAllByRoleId(Long roleId);
    void deleteAllByRoleId(Long roleId);
}

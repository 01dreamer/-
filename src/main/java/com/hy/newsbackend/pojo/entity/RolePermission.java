package com.hy.newsbackend.pojo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * 角色与权限关系
 *
 * @author dhb
 */
@Getter
@Setter
@Entity(name = "sys_role_permissions")
public class RolePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "roleId")
    private Long roleId;

    @Column(name = "permissionId")
    private Long permissionId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolePermission that = (RolePermission) o;
        return id == that.id && Objects.equals(roleId, that.roleId) && Objects.equals(permissionId, that.permissionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleId, permissionId);
    }
}

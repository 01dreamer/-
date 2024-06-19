package com.hy.newsbackend.pojo.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
@Table(name = "sys_permission")
@DynamicInsert
@DynamicUpdate
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Permission implements Serializable {

    private static final long serialVersionUID = -54979041104113736L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "permission_name", nullable = false, length = 64)
    private String permissionName;

    @Column(length = 200)
    private String path;

    @Column(length = 255)
    private String component;

    @Column(length = 1, columnDefinition = "char(1) default '0'")
    private String visible;

    @Column(length = 1, columnDefinition = "char(1) default '0'")
    private String status;

    @Column(length = 100)
    private String perms;

    @Column(length = 100, columnDefinition = "varchar(100) default '#'")
    private String icon;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createTime;

    @Column(name = "update_by")
    private Long updateBy;

    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updateTime;

    @Column(name = "del_flag", columnDefinition = "int default 0")
    private Integer delFlag;

    @Column(length = 500)
    private String remark;

    public Permission(){
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(id, that.id) && Objects.equals(permissionName, that.permissionName) && Objects.equals(path, that.path) && Objects.equals(component, that.component) && Objects.equals(visible, that.visible) && Objects.equals(status, that.status) && Objects.equals(perms, that.perms) && Objects.equals(icon, that.icon) && Objects.equals(createBy, that.createBy) && Objects.equals(createTime, that.createTime) && Objects.equals(updateBy, that.updateBy) && Objects.equals(updateTime, that.updateTime) && Objects.equals(delFlag, that.delFlag) && Objects.equals(remark, that.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, permissionName, path, component, visible, status, perms, icon, createBy, createTime, updateBy, updateTime, delFlag, remark);
    }
}

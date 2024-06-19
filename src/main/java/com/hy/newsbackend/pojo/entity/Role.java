package com.hy.newsbackend.pojo.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.Objects;


@Data
@Entity
@Table(name = "sys_role")
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128)
    private String name;

    @Column(name = "role_key",length = 100)
    private String roleKey;

    @Column(length = 1)
    private String status;

    @Column(name = "del_flag")
    private Integer delFlag;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_by")
    private Long updateBy;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(length = 500)
    private String remark;
    public Role(){
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role that = (Role) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(roleKey, that.roleKey) && Objects.equals(status, that.status) && Objects.equals(delFlag, that.delFlag) && Objects.equals(createBy, that.createBy) && Objects.equals(createTime, that.createTime) && Objects.equals(updateBy, that.updateBy) && Objects.equals(updateTime, that.updateTime) && Objects.equals(remark, that.remark) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, roleKey, status, delFlag, createBy, createTime, updateBy, updateTime, remark);
    }
}


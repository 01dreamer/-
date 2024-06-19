package com.hy.newsbackend.pojo.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
@Table(name = "sys_user")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 497586889556617583L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name",nullable = false, length = 64)
    private String userName;

    @Column(name = "nick_name",nullable = false, length = 64)
    private String nickName;

    // 阻止JSON序列化
    @JSONField(serialize = false)
    @Column(nullable = false, length = 64)
    private String password;

    @Column(length = 1)
    private String status;

    @Column(length = 64)
    private String email;

    @Column(name = "phonenumber",length = 30)
    private String phoneNumber;

    @Column(length = 1)
    private String sex;

    @Column(length = 128)
    private String avatar;

    @Column(name = "user_type",nullable = false, length = 1)
    private String userType;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_by")
    private Long updateBy;

    @Column(name = "update_time")
    private LocalDateTime updateTime;
    private Integer deleted;

    public User(){
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return Objects.equals(id, that.id) && Objects.equals(userName, that.userName) && Objects.equals(nickName, that.nickName) && Objects.equals(password, that.password) && Objects.equals(status, that.status) && Objects.equals(email, that.email) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(sex,that.sex) && Objects.equals(avatar,that.avatar) && Objects.equals(userType,that.userType) && Objects.equals(createBy, that.createBy) && Objects.equals(createTime, that.createTime) && Objects.equals(updateBy, that.updateBy) && Objects.equals(updateTime, that.updateTime) && Objects.equals(deleted, that.deleted) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, nickName, password, status, email, phoneNumber, sex , avatar , userType , createBy, createTime, updateBy, updateTime, deleted);
    }
}

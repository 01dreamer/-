
//	此资源由 58学课资源站 收集整理
//	想要获取完整课件资料 请访问：58xueke.com
//	百万资源 畅享学习
package com.hy.newsbackend.common.result;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义响应数据类型枚举
 */
@Setter
@Getter
public class R {

    // 响应业务状态码
    private Integer status;

    // 响应消息
    private String msg;

    // 是否成功
    private Boolean success;

    // 响应数据，可以是Object，也可以是List或Map等
    private Object data;

    /**
     * 成功返回，带有数据的，直接往OK方法丢data数据即可
     * @param data 把传入的数据封装成json
     * @return 返回json中包含data的api
     */
    public static R ok(Object data) {
        return new R(data);
    }

    public static R token(String key,String value) {
        Map<String, String> jwtMap = new HashMap<>();
        jwtMap.put(key,value);
        return new R(jwtMap);
    }


    /**
     * 成功返回，不带有数据的，直接调用ok方法，data无须传入（其实就是null）
     * @return 请求成功直接响应
     */
    public static R ok() {
        return new R(ResponseStatusEnum.SUCCESS);
    }
    public R(Object data) {
        this.status = ResponseStatusEnum.SUCCESS.status();
        this.msg = ResponseStatusEnum.SUCCESS.msg();
        this.success = ResponseStatusEnum.SUCCESS.success();
        this.data = data;
    }


    /**
     * 错误返回，直接调用error方法即可，当然也可以在ResponseStatusEnum中自定义错误后再返回也都可以
     * @return 错误返回默认错误状态
     */
    public static R error() {
        return new R(ResponseStatusEnum.FAILED);
    }

    /**
     * 错误返回，map中包含了多条错误信息，可以用于表单验证，把错误统一的全部返回出去
     * @param map 包装多条错误信息
     * @return 返回包含多条错误信息
     */
    public static R errorMap(Map<String,Object> map) {
        return new R(ResponseStatusEnum.FAILED, map);
    }

    /**
     * 错误返回，直接返回错误的消息
     * @param msg 包装单条错误信息
     * @return 返回json中包含单条错误信息
     */
    public static R errorMsg(String msg) {
        return new R(ResponseStatusEnum.FAILED, msg);
    }

    /**
     * 错误返回，token异常，一些通用的可以在这里统一定义
     * @return token异常时返回
     */
    public static R errorTicket() {
        return new R(ResponseStatusEnum.TICKET_INVALID);
    }

    /**
     * 自定义错误范围，需要传入一个自定义的枚举，可以到[ResponseStatusEnum.java[中自定义后再传入
     * @param responseStatus 自定义枚举
     * @return 返回json中包含自定义枚举
     */
    public static R errorCustom(ResponseStatusEnum responseStatus) {
        return new R(responseStatus);
    }
    public static R exception(ResponseStatusEnum responseStatus) {
        return new R(responseStatus);
    }

    public R(ResponseStatusEnum responseStatus) {
        this.status = responseStatus.status();
        this.msg = responseStatus.msg();
        this.success = responseStatus.success();
    }
    public R(ResponseStatusEnum responseStatus, Object data) {
        this.status = responseStatus.status();
        this.msg = responseStatus.msg();
        this.success = responseStatus.success();
        this.data = data;
    }
    public R(ResponseStatusEnum responseStatus, String msg) {
        this.status = responseStatus.status();
        this.msg = msg;
        this.success = responseStatus.success();
    }

    public R() {
    }

}

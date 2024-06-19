package com.hy.newsbackend.admin.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HelloController {
    // 权限的操作：只要拿上jwt，就可以访问，暂时不需要赋权
    @GetMapping
    @PreAuthorize("hasAnyAuthority('select')")      // 权限本身就是使用字符串来进行管理的
    public Object hello(){
        return "hello Security~~";
    }
}

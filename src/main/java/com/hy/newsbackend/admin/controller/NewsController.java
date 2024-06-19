package com.hy.newsbackend.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("news")
@RequiredArgsConstructor
public class NewsController {
    @GetMapping
    @PreAuthorize("hasAnyAuthority('sys:news:select')")
    public String getNews(){
        return "news列表";
    }
    @PostMapping("add")
    @PreAuthorize("hasAnyAuthority('sys:news:add')")
    public String addNews(){
        return "保存成功";
    }
    @PutMapping("update")
    @PreAuthorize("hasAnyAuthority('sys:news:update')")
    public String updateNews(){
        return "修改成功";
    }@DeleteMapping("delete")
    @PreAuthorize("hasAnyAuthority('sys:news:delete')")
    public String deleteNews(){
        return "删除成功";
    }
}

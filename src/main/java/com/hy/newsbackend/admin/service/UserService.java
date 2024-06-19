package com.hy.newsbackend.admin.service;

import com.hy.newsbackend.common.result.R;
import com.hy.newsbackend.pojo.entity.User;

public interface UserService {
    R login(User user);
}

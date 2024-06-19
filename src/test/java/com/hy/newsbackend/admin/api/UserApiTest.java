package com.hy.newsbackend.admin.api;

import com.hy.newsbackend.admin.repository.UserRepository;
import com.hy.newsbackend.pojo.entity.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserApiTest {
    @Resource
    private UserRepository userRepository;
    @Test
    void userRepositoryTest(){
        List<User> users = userRepository.findAll();
        users.forEach(System.out::println);
    }
}

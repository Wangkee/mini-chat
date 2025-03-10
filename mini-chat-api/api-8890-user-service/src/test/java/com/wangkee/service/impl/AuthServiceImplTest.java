package com.wangkee.service.impl;
import com.wangkee.service.AuthService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class AuthServiceImplTest {

    @Resource
    private AuthServiceImpl authService;

    @Test
    public void testCreateUser(){
        authService.createUser("18800000001");
    }



}
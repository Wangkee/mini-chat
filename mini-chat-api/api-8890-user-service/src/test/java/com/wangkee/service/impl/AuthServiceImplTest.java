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
    public void testCreateUsers() {
        for (long i = 18800000000L; i < 18800000000L + 100000; i++) {
            authService.createMockUser(String.valueOf(i));
        }
    }




}
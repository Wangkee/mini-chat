package com.wangkee.service.impl;

import com.wangkee.dto.FollowshipDTO;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FollowServiceImplTest {

    @Resource
    private FollowServiceImpl followService;

    @Test
    void addFollow() {
        followService.addFollow(new FollowshipDTO(1899053711183839232L, 1899053885616553984L, "test", null, null));
    }

    @Test
    void deleteFollow() {
        followService.deleteFollow(new FollowshipDTO(1899053711183839232L, 1899053885616553984L, null, null, null));
    }
}
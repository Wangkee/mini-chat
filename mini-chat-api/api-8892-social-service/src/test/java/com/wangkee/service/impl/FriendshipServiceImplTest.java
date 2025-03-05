package com.wangkee.service.impl;

import com.wangkee.po.Friendship;
import com.wangkee.service.FriendshipService;
import jakarta.annotation.Resource;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class FriendshipServiceImplTest {

    @Resource
    private FriendshipService friendshipService;

    @Test
    public void testAddFriendShip(){
        friendshipService.save(new Friendship(
                1L, 1L, 1L, "axxx", null, null, 12323L, 132L
        ));
    }
}
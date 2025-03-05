package com.wangkee.mapper;

import com.wangkee.vo.FriendShipBriefVO;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class FriendshipMapperTest {

    @Resource
    private FriendshipMapper friendshipMapper;

    @Test
    void queryMyFriends() {
        List<FriendShipBriefVO> friendShipBriefVOS = friendshipMapper.queryMyFriends(1894731842343403520L, false);
        System.out.println(friendShipBriefVOS);
    }
}
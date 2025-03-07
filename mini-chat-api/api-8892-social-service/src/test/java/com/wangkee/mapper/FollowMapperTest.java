package com.wangkee.mapper;

import com.wangkee.vo.FollowListItemVO;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FollowMapperTest {

    @Resource
    private FollowMapper followMapper;

    @Test
    public void testQueryFollowingList(){
        List<FollowListItemVO> followListItemVOS = followMapper.queryFollowingList(1L);
        System.out.println(followListItemVOS);
    }

    @Test
    public void testQueryFollowerList(){
        List<FollowListItemVO> followListItemVOS = followMapper.queryFollowerList(1L);
        System.out.println(followListItemVOS);
    }
}
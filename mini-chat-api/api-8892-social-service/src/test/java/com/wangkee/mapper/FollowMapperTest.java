package com.wangkee.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangkee.utils.PagedResult;
import com.wangkee.vo.FollowListItemVO;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FollowMapperTest {

    @Resource
    private FollowMapper followMapper;

//    @Test
//    public void testQueryFollowingList(){
//        Page<FollowListItemVO> result = followMapper.queryFollowingList(new Page<>(1L,1L),1L);
//        System.out.println(PagedResult.assemblePagedGridResult(result));
//    }
//
//    @Test
//    public void testQueryFollowerList(){
//        Page<FollowListItemVO> result = followMapper.queryFollowerList(new Page<>(1L,1L),1L);
//        System.out.println(PagedResult.assemblePagedGridResult(result));
//    }
}
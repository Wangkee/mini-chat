package com.wangkee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangkee.mapper.FollowMapper;
import com.wangkee.mapper.UserMapper;
import com.wangkee.po.Follow;
import com.wangkee.service.FollowService;
import com.wangkee.vo.FollowListItemVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow>
    implements FollowService {

    @Resource
    private FollowMapper followMapper;

    @Override
    public List<FollowListItemVO> queryFollowingList(Long userId) {
        return followMapper.queryFollowingList(userId);
    }

    @Override
    public List<FollowListItemVO> queryFollowerList(Long userId) {
        return followMapper.queryFollowerList(userId);
    }


}





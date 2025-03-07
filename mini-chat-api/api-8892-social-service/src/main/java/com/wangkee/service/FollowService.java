package com.wangkee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangkee.po.Follow;
import com.wangkee.vo.FollowListItemVO;

import java.util.List;


public interface FollowService extends IService<Follow> {

    List<FollowListItemVO> queryFollowingList(Long userId);

    List<FollowListItemVO> queryFollowerList(Long userId);
}

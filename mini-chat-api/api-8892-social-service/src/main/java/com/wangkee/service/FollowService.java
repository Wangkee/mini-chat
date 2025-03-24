package com.wangkee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangkee.dto.FollowshipDTO;
import com.wangkee.po.Follow;
import com.wangkee.utils.PagedResult;
import com.wangkee.vo.FollowListItemVO;

import java.util.List;


public interface FollowService extends IService<Follow> {

    List<FollowListItemVO> queryFollowingList(Integer page, Integer pageSize,Long userId);

    List<FollowListItemVO> queryFollowerList(Integer page, Integer pageSize, Long userId);

    void addFollow(FollowshipDTO dto);

    void deleteFollow(FollowshipDTO dto);

    void updateFollow(FollowshipDTO dto);
}

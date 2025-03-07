package com.wangkee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangkee.po.Follow;
import com.wangkee.vo.FollowListItemVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface FollowMapper extends BaseMapper<Follow> {

    List<FollowListItemVO> queryFollowingList(Long userId);

    List<FollowListItemVO> queryFollowerList(Long userId);
}





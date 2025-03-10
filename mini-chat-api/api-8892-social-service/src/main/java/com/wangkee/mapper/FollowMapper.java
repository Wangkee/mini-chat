package com.wangkee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangkee.po.Follow;
import com.wangkee.vo.FollowListItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface FollowMapper extends BaseMapper<Follow> {

    Page<FollowListItemVO> queryFollowingList(
            @Param("page")Page<FollowListItemVO> page,
            @Param("userId")Long userId);

    Page<FollowListItemVO> queryFollowerList(
            @Param("page")Page<FollowListItemVO> page,
            @Param("userId")Long userId);
}





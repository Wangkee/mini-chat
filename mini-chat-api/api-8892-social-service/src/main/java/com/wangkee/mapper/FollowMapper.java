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

    List<FollowListItemVO> queryFollowingList(
            @Param("offset")Integer offset,
            @Param("pageSize")Integer pageSize,
            @Param("userId")Long userId);

    List<FollowListItemVO> queryFollowerList(
            @Param("offset")Integer offset,
            @Param("pageSize")Integer pageSize,
            @Param("userId")Long userId);
}





package com.wangkee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangkee.po.Friendship;
import com.wangkee.vo.FriendShipBriefVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface FriendshipMapper extends BaseMapper<Friendship> {

    List<FriendShipBriefVO> queryMyFriends(
            @Param("userId") Long userId,
            @Param("isBlocked")Boolean isBlocked
    );
}





package com.wangkee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangkee.po.Friendship;
import com.wangkee.vo.FriendDetailVO;
import com.wangkee.vo.FriendShipBriefVO;

import java.util.List;


public interface FriendshipService extends IService<Friendship> {

    FriendDetailVO queryFriendshipInfo(Long userId, Long friendId);

    List<FriendShipBriefVO> queryFriends(Long userId, Boolean isBlocked);

    void updateRemark(Long userId, Long friendId, String newRemark);

    void updateBlockStatus(Long userId, Long friendId, Boolean isBlocked);

    void deleteFriend(Long userId, Long friendId);
}

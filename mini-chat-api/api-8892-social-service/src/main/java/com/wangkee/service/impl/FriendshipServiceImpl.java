package com.wangkee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangkee.exceptions.BusinessException;
import com.wangkee.mapper.FriendshipMapper;
import com.wangkee.mapper.UserMapper;
import com.wangkee.po.Friendship;
import com.wangkee.po.User;
import com.wangkee.result.ResponseStatusEnum;
import com.wangkee.service.FriendshipService;
import com.wangkee.vo.FriendDetailVO;
import com.wangkee.vo.FriendShipBriefVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;


@Service
public class FriendshipServiceImpl extends ServiceImpl<FriendshipMapper, Friendship>
    implements FriendshipService {

    @Resource
    private FriendshipMapper friendshipMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public List<FriendShipBriefVO> queryFriends(Long userId, Boolean isBlocked) {
        User user = userMapper.selectById(userId);
        if(Objects.isNull(user)){
            throw new BusinessException(ResponseStatusEnum.USER_NOT_EXIST);
        }

        return friendshipMapper.queryMyFriends(userId, isBlocked);
    }

    @Override
    public void updateRemark(Long userId, Long friendId, String newRemark) {
        Friendship friendship = checkFriendShipExistence(userId, friendId);
        friendship.setRemark(newRemark);
        updateById(friendship);
    }

    @Override
    public void updateBlockStatus(Long userId, Long friendId, Boolean isBlocked) {
        Friendship friendship = checkFriendShipExistence(userId, friendId);
        friendship.setBlocked(isBlocked);
        updateById(friendship);
    }

    @Override
    @Transactional
    public void deleteFriend(Long userId, Long friendId) {
        Friendship friendship1 = checkFriendShipExistence(userId, friendId);
        Friendship friendship2 = checkFriendShipExistence(friendId, userId);

        removeById(friendship1);
        removeById(friendship2);
    }

    @Override
    public FriendDetailVO queryFriendshipInfo(Long userId, Long friendId) {
        Friendship friendship = checkFriendShipExistence(userId, friendId);
        User friend = userMapper.selectById(friendId);

        return assembleFriendDetail(friend, friendship);
    }

    private Friendship checkFriendShipExistence(Long userId, Long friendId){
        LambdaQueryWrapper<Friendship> queryWrapper = new LambdaQueryWrapper<Friendship>()
                .eq(Friendship::getUserId, userId)
                .eq(Friendship::getFriendId, friendId);
        Friendship friendship = getOne(queryWrapper);

        if(Objects.isNull(friendId)){
            throw new BusinessException(ResponseStatusEnum.FRIENDSHIP_NOT_EXIST);
        }

        return friendship;
    }

    private FriendDetailVO assembleFriendDetail(User user, Friendship friendship){
        FriendDetailVO friendDetailVO = new FriendDetailVO();

        // 从 Friendship 对象中获取数据
        friendDetailVO.setFriendShipId(friendship.getId());
        friendDetailVO.setUserId(friendship.getUserId());
        friendDetailVO.setFriendId(friendship.getFriendId());
        friendDetailVO.setRemark(friendship.getRemark());
        friendDetailVO.setChatOnly(friendship.getChatOnly());
        friendDetailVO.setBlocked(friendship.getBlocked());

        // 从 User 对象中获取数据
        friendDetailVO.setChatNum(user.getChatNum());
        friendDetailVO.setNickname(user.getNickname());
        friendDetailVO.setGender(user.getGender());
        friendDetailVO.setBirthday(user.getBirthday());
        friendDetailVO.setAvatar(user.getAvatar());
        friendDetailVO.setSignature(user.getSignature());
        friendDetailVO.setSchool(user.getSchool());
        friendDetailVO.setLocation(user.getLocation());
        friendDetailVO.setHomepageImg(user.getHomepageImg());

        // 计算成为好友的天数
        LocalDate now = LocalDate.now();
        LocalDate friendSince = LocalDateTime.ofEpochSecond(friendship.getCreatedAt(), 0, java.time.ZoneOffset.UTC).toLocalDate();
        long days = ChronoUnit.DAYS.between(friendSince, now);
        friendDetailVO.setFriendshipDays((int) days);

        return friendDetailVO;
    }
}





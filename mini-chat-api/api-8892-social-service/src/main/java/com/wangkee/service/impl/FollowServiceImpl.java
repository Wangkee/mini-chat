package com.wangkee.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangkee.dto.FollowshipDTO;
import com.wangkee.exceptions.BusinessException;
import com.wangkee.mapper.FollowCountMapper;
import com.wangkee.mapper.FollowMapper;
import com.wangkee.mapper.UserMapper;
import com.wangkee.po.Follow;
import com.wangkee.po.FollowCount;
import com.wangkee.po.User;
import com.wangkee.result.ResponseStatusEnum;
import com.wangkee.service.FollowService;
import com.wangkee.utils.PagedResult;
import com.wangkee.utils.SnowFlakeUtil;
import com.wangkee.vo.FollowListItemVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow>
    implements FollowService {

    @Resource
    private FollowMapper followMapper;

    @Resource
    private FollowCountMapper followCountMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public List<FollowListItemVO> queryFollowingList(Integer page, Integer pageSize, Long userId) {
        int offset = (page - 1) * pageSize;
        return followMapper.queryFollowingList(offset, pageSize, userId);
    }

    @Override
    public List<FollowListItemVO> queryFollowerList(Integer page, Integer pageSize,Long userId) {
        int offset = (page - 1) * pageSize;
        return followMapper.queryFollowerList(offset, pageSize, userId);
    }

    @Override
    @Transactional
    public void addFollow(FollowshipDTO dto) {
        checkUserExistence(dto.getFollowee());
        checkUserExistence(dto.getFollower());
        checkFollowShipExistence(dto.getFollower(), dto.getFollowee(), true);

        Long followShipId = SnowFlakeUtil.nextId();;
        Long currentTime = System.currentTimeMillis();

        Follow follow = new Follow();
        follow.setId(followShipId);
        follow.setFollowee(dto.getFollowee());
        follow.setFollower(dto.getFollower());
        follow.setRemark(dto.getRemark());
        follow.setCreatedAt(currentTime);
        follow.setUpdatedAt(currentTime);

        followMapper.insert(follow);

        FollowCount followerFollowCount = followCountMapper.selectById(dto.getFollower());
        followerFollowCount.setFollowingCount(followerFollowCount.getFollowingCount() + 1);
        followCountMapper.updateById(followerFollowCount);

        FollowCount followeeFollowCount = followCountMapper.selectById(dto.getFollowee());
        followeeFollowCount.setFollowerCount(followeeFollowCount.getFollowerCount() + 1);
        followCountMapper.updateById(followeeFollowCount);

    }

    @Override
    @Transactional
    public void deleteFollow(FollowshipDTO dto) {
        checkUserExistence(dto.getFollowee());
        checkUserExistence(dto.getFollower());
        checkFollowShipExistence(dto.getFollower(), dto.getFollowee(), false);

        followMapper.delete(
                new LambdaQueryWrapper<Follow>()
                        .eq(Follow::getFollower, dto.getFollower())
                        .eq(Follow::getFollowee, dto.getFollowee())
        );

        FollowCount followerFollowCount = followCountMapper.selectById(dto.getFollower());
        followerFollowCount.setFollowingCount(followerFollowCount.getFollowingCount() - 1);
        followCountMapper.updateById(followerFollowCount);

        FollowCount followeeFollowCount = followCountMapper.selectById(dto.getFollowee());
        followeeFollowCount.setFollowerCount(followeeFollowCount.getFollowerCount() - 1);
        followCountMapper.updateById(followeeFollowCount);
    }

    @Override
    public void updateFollow(FollowshipDTO dto) {
        checkFollowShipExistence(dto.getFollower(), dto.getFollowee(), false);

        Follow follow = followMapper.selectOne(
                new LambdaQueryWrapper<Follow>()
                        .eq(Follow::getFollower, dto.getFollower())
                        .eq(Follow::getFollowee, dto.getFollowee())
        );

        follow.setTotop(dto.getTotop());
        follow.setMuted(dto.getMuted());
        follow.setRemark(dto.getRemark());
        followMapper.updateById(follow);

    }

    private void checkUserExistence(Long userId){
        User user = userMapper.selectById(userId);
        if(Objects.isNull(user)){
            throw new BusinessException(ResponseStatusEnum.USER_NOT_EXIST);
        }
    }

    private void checkFollowShipExistence(Long followerId, Long followeeId, boolean checkUnExistence){
        Follow follow = followMapper.selectOne(
                new LambdaQueryWrapper<Follow>()
                        .eq(Follow::getFollower, followerId)
                        .eq(Follow::getFollowee, followeeId)
        );
        if(!Objects.isNull(follow) && checkUnExistence){
            throw new BusinessException(ResponseStatusEnum.FOLLOWSHIP_ALREADY_EXIST);
        }

        if(Objects.isNull(follow) && !checkUnExistence){
            throw new BusinessException(ResponseStatusEnum.FOLLOWSHIP_NOT_EXIST);
        }
    }


    @Transactional
    public void addFollowBatch(List<FollowshipDTO> dtoList) {
        // 获取 Snowflake ID
        Long currentTime = System.currentTimeMillis();

        // 批量插入关注关系
        List<Follow> follows = new ArrayList<>();
        for (FollowshipDTO dto : dtoList) {
            Long followShipId = SnowFlakeUtil.nextId();;

            Follow follow = new Follow();
            follow.setId(followShipId);
            follow.setFollowee(dto.getFollowee());
            follow.setFollower(dto.getFollower());
            follow.setRemark(dto.getRemark());
            follow.setCreatedAt(currentTime);
            follow.setUpdatedAt(currentTime);

            follows.add(follow);
        }

        // 批量插入到数据库
        saveBatch(follows);

        // 批量更新关注数量
        Map<Long, Integer> followerUpdateMap = new HashMap<>();
        Map<Long, Integer> followeeUpdateMap = new HashMap<>();

        for (FollowshipDTO dto : dtoList) {
            followerUpdateMap.put(dto.getFollower(), followerUpdateMap.getOrDefault(dto.getFollower(), 0) + 1);
            followeeUpdateMap.put(dto.getFollowee(), followeeUpdateMap.getOrDefault(dto.getFollowee(), 0) + 1);
        }

        // 批量更新关注者的 FollowingCount 和 被关注者的 FollowerCount
        for (Map.Entry<Long, Integer> entry : followerUpdateMap.entrySet()) {
            Long followerId = entry.getKey();
            Integer countToAdd = entry.getValue();

            FollowCount followerFollowCount = followCountMapper.selectById(followerId);
            if (followerFollowCount != null) {
                followerFollowCount.setFollowingCount(followerFollowCount.getFollowingCount() + countToAdd);
                followCountMapper.updateById(followerFollowCount);
            }
        }

        for (Map.Entry<Long, Integer> entry : followeeUpdateMap.entrySet()) {
            Long followeeId = entry.getKey();
            Integer countToAdd = entry.getValue();

            FollowCount followeeFollowCount = followCountMapper.selectById(followeeId);
            if (followeeFollowCount != null) {
                followeeFollowCount.setFollowerCount(followeeFollowCount.getFollowerCount() + countToAdd);
                followCountMapper.updateById(followeeFollowCount);
            }
        }
    }
}





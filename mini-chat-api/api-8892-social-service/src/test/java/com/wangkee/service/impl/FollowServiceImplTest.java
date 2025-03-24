package com.wangkee.service.impl;

import com.wangkee.dto.FollowshipDTO;
import com.wangkee.mapper.FollowMapper;
import com.wangkee.service.FollowService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class FollowServiceImplTest {



    @Autowired
    private FollowServiceImpl followService;

    @Resource
    private FollowMapper followMapper;

    private static final long START_USER_ID = 18800000000L;
    private static final int USER_COUNT = 100000;
    private static final int TOP_USERS = USER_COUNT / 100; // 1% 头部博主 (1000)
    private static final int MID_USERS = USER_COUNT * 5 / 100; // 5% 次头部博主 (5000)



    @Test
    public void addFollowBatch() {
        Random random = new Random();
        List<Long> userIds = new ArrayList<>();

        // 生成用户 ID 列表
        for (long i = START_USER_ID; i < START_USER_ID + USER_COUNT; i++) {
            userIds.add(i);
        }

        // 划分不同层级的用户
        List<Long> topUsers = userIds.subList(0, TOP_USERS);  // 头部博主
        List<Long> midUsers = userIds.subList(TOP_USERS, TOP_USERS + MID_USERS);  // 次头部博主
        List<Long> normalUsers = userIds.subList(TOP_USERS + MID_USERS, USER_COUNT);  // 普通用户

        // 存储关注关系，避免重复
        Set<String> followSet = new HashSet<>();

        // 1. **所有用户先生成一定数量的互关好友**
        int mutualCount = 10; // 每个用户至少有 5 个互关好友
        for (Long userId : userIds) {
            List<Long> shuffledUsers = new ArrayList<>(userIds);
            shuffledUsers.remove(userId);
            Collections.shuffle(shuffledUsers);

            List<FollowshipDTO> follows = new ArrayList<>();
            for (int i = 0; i < mutualCount && i < shuffledUsers.size(); i++) {
                Long friendId = shuffledUsers.get(i);
                if (!followSet.contains(userId + "-" + friendId) && !followSet.contains(friendId + "-" + userId)) {
                    follows.add(new FollowshipDTO(userId, friendId, null, null, null));
                    follows.add(new FollowshipDTO(friendId, userId, null, null, null));
                    followSet.add(userId + "-" + friendId);
                    followSet.add(friendId + "-" + userId);
                }
            }
            followService.addFollowBatch(follows);
        }

        // 2. **普通用户随机关注 10 ~ 200 人**
        for (Long userId : normalUsers) {
            int followCount = random.nextInt(191) + 10; // [10, 200]
            List<Long> shuffledUsers = new ArrayList<>(userIds);
            Collections.shuffle(shuffledUsers);

            List<FollowshipDTO> follows = new ArrayList<>();
            int added = 0;
            for (Long followeeId : shuffledUsers) {
                if (!userId.equals(followeeId) && !followSet.contains(userId + "-" + followeeId)) {
                    follows.add(new FollowshipDTO(userId, followeeId, null, null, null));
                    followSet.add(userId + "-" + followeeId);
                    if (++added >= followCount) break;
                }
            }
            followService.addFollowBatch(follows);
        }

        // 3. **头部博主 (1%) 获得 1000 ~ 20000 粉丝**
        for (Long topUser : topUsers) {
            int fanCount = random.nextInt(20000 - 1000 + 1) + 1000; // [1000, 20000]
            List<Long> availableFans = new ArrayList<>(userIds);
            availableFans.remove(topUser);
            Collections.shuffle(availableFans);

            List<FollowshipDTO> follows = new ArrayList<>();
            for (int i = 0; i < fanCount && i < availableFans.size(); i++) {
                Long fanId = availableFans.get(i);
                if (!followSet.contains(fanId + "-" + topUser)) {
                    follows.add(new FollowshipDTO(fanId, topUser, null, null, null));
                    followSet.add(fanId + "-" + topUser);
                }
            }
            followService.addFollowBatch(follows);
        }

        // 4. **次头部博主 (5%) 获得 200 ~ 1000 粉丝**
        for (Long midUser : midUsers) {
            int fanCount = random.nextInt(801) + 200; // [200, 1000]
            List<Long> availableFans = new ArrayList<>(userIds);
            availableFans.remove(midUser);
            Collections.shuffle(availableFans);

            List<FollowshipDTO> follows = new ArrayList<>();
            for (int i = 0; i < fanCount && i < availableFans.size(); i++) {
                Long fanId = availableFans.get(i);
                if (!followSet.contains(fanId + "-" + midUser)) {
                    follows.add(new FollowshipDTO(fanId, midUser, null, null, null));
                    followSet.add(fanId + "-" + midUser);
                }
            }
            followService.addFollowBatch(follows);
        }
    }

}
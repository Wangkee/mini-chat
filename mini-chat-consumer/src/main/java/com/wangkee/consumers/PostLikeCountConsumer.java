package com.wangkee.consumers;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wangkee.constants.BusinessConstants;
import com.wangkee.mapper.PostLikeCountMapper;
import com.wangkee.mapper.PostLikeMapper;
import com.wangkee.po.PostLike;
import com.wangkee.po.PostLikeCount;
import com.wangkee.utils.RedisOperator;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RocketMQMessageListener(topic = "post-like-count", consumerGroup = "my-consumer")
public class PostLikeCountConsumer implements RocketMQListener<PostLike> {

    @Resource
    private PostLikeMapper postLikeMapper;

    @Resource
    private PostLikeCountMapper postLikeCountMapper;

    @Resource
    private RedisOperator redisOperator;

    private final Map<Long, Integer> likeCount = new ConcurrentHashMap<>();

    public void onMessage(PostLike postLike) {
        log.info("receive postlike message: {}", postLike);

        postLikeMapper.insert(postLike);
        likeCount.compute(postLike.getPostId(), (k, v) -> (v == null) ? 1 : v + 1);
    }

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
    public void updateDatabase() {
        if (likeCount.isEmpty()) {
            return;
        }

        likeCount.forEach((postId, count) -> {
            int currentLikeCount = likeCount.get(postId);
            if (currentLikeCount == 0) {
                return;
            }

            LambdaUpdateWrapper<PostLikeCount> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(PostLikeCount::getPostId, postId)
                    .setSql("count = count + " + count);
            postLikeCountMapper.update(null, updateWrapper);

            redisOperator.delete(BusinessConstants.POST_LIKE_COUNT + postId);

            likeCount.computeIfPresent(postId, (key, oldValue) -> oldValue - currentLikeCount);
        });
    }
}

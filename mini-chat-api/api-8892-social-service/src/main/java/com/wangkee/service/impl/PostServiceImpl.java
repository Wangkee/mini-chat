package com.wangkee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangkee.constants.BusinessConstants;
import com.wangkee.dto.AddPostDTO;
import com.wangkee.enums.VisibilityEnum;
import com.wangkee.exceptions.BusinessException;
import com.wangkee.mapper.*;
import com.wangkee.po.*;
import com.wangkee.result.ResponseStatusEnum;
import com.wangkee.service.PostService;
import com.wangkee.utils.RedisOperator;
import com.wangkee.utils.SnowFlakeUtil;
import jakarta.annotation.Resource;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post>
    implements PostService {

    @Resource
    private  PostMapper postMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private PostTagMapper postTagMapper;

    @Resource
    private PostLikeMapper postLikeMapper;

    @Resource
    private PostLikeCountMapper postLikeCountMapper;

    @Resource
    private PostVisibilityBlacklistMapper blacklistMapper;

    @Resource
    private PostVisibilityWhitelistMapper whitelistMapper;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Resource
    private RedisOperator redisOperator;


    public PostServiceImpl(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    @Override
    @Transactional
    public long addPost(AddPostDTO dto) {
        Long postId = SnowFlakeUtil.nextId();
        Long currentTime = System.currentTimeMillis();

        Post post = new Post();
        post.setId(postId);
        post.setUserId(dto.getUserId());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setImages(dto.getImages());
        post.setVideoUrl(dto.getVideoUrl());
        post.setLocation(dto.getLocation());
        post.setVisibility(dto.getVisibility());
        post.setStatus(dto.getStatus());
        post.setCreatedAt(currentTime);
        post.setUpdatedAt(currentTime);
        save(post);

        if(dto.getVisibility().equals(VisibilityEnum.PARTIAL_VISIBLE.getCode())){
            List<Long> whiteList = dto.getWhiteList();
            for (Long userId : whiteList) {
                PostVisibilityWhitelist whitelist = new PostVisibilityWhitelist(postId, userId);
                whitelistMapper.insert(whitelist);
            }
        }else if(dto.getVisibility().equals(VisibilityEnum.PARTIAL_INVISIBLE.getCode())){
            List<Long> blackList = dto.getBlackList();
            for (Long userId : blackList) {
                PostVisibilityBlacklist blacklist = new PostVisibilityBlacklist(postId, userId);
                blacklistMapper.insert(blacklist);
            }
        }

        for(Long tagId : dto.getTags()){
            PostTag postTag = new PostTag(postId, tagId);
            postTagMapper.insert(postTag);
        }

        PostLikeCount postLikeCount = new PostLikeCount(postId, 0L);
        postLikeCountMapper.insert(postLikeCount);

        return postId;
    }

    @Override
    public void queryPostList(Long userId, Long userId1, Integer page, Integer pageSize) {

    }

    @Override
    @Transactional
    public void likePost(Long userId, Long postId) {
        Post post = postMapper.selectById(postId);
        if(Objects.isNull(post)){
            throw new BusinessException(ResponseStatusEnum.POST_NOT_EXIST);
        }

        LambdaQueryWrapper<PostLike> queryWrapper = new LambdaQueryWrapper<PostLike>()
                .eq(PostLike::getUserId, userId)
                .eq(PostLike::getPostId, postId);

        if(postLikeMapper.exists(queryWrapper)){
            throw new BusinessException(ResponseStatusEnum.POST_ALREADY_LIKED);
        }

        PostLike postLike = new PostLike();
        postLike.setId(SnowFlakeUtil.nextId());
        postLike.setUserId(userId);
        postLike.setPostId(postId);
        postLike.setCreatedAt(System.currentTimeMillis());

        rocketMQTemplate.convertAndSend("post-like-count", postLike);
    }

    /**
     * 查询点赞数，采用CacheAside策略
     * 先查redis缓存，如果命中直接返回，否则查询数据库，并更新redis缓存
     */
    @Override
    public long queryLikeCount(Long postId) {
        Post post = postMapper.selectById(postId);
        if(Objects.isNull(post)){
            throw new BusinessException(ResponseStatusEnum.POST_NOT_EXIST);
        }

        String cachedResult = redisOperator.get(BusinessConstants.POST_LIKE_COUNT + postId);
        if(Objects.nonNull(cachedResult)){
            return Integer.parseInt(cachedResult);
        }

        long postLikeCount = postLikeCountMapper.selectById(postId).getCount();
        redisOperator.set(
                BusinessConstants.POST_LIKE_COUNT + postId,
                String.valueOf(postLikeCount),
                BusinessConstants.POST_LIKE_COUNT_TTL,
                TimeUnit.SECONDS
        );

        return postLikeCount;
    }

    @Override
    public boolean queryCheckPostLike(Long userId, Long postId) {
        LambdaQueryWrapper<PostLike> queryWrapper = new LambdaQueryWrapper<PostLike>()
                .eq(PostLike::getUserId, userId)
                .eq(PostLike::getPostId, postId);

        return postLikeMapper.exists(queryWrapper);
    }
}





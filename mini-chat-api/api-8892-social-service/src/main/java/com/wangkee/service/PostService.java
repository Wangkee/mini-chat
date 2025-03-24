package com.wangkee.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wangkee.dto.AddPostDTO;
import com.wangkee.po.Post;


public interface PostService extends IService<Post> {

    long addPost(AddPostDTO dto);

    void queryPostList(Long userId, Long userId1, Integer page, Integer pageSize);

    void likePost(Long userId, Long postId);

    long queryLikeCount(Long postId);

    boolean queryCheckPostLike(Long userId, Long postId);
}

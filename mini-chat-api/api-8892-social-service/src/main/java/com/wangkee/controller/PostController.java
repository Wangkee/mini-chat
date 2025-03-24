package com.wangkee.controller;


import cn.hutool.json.JSON;
import com.wangkee.dto.AddPostDTO;
import com.wangkee.result.JSONResult;
import com.wangkee.service.PostService;
import com.wangkee.utils.UserContext;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/post")
public class PostController {

    @Resource
    private PostService postService;

    @PostMapping("/add")
    public JSONResult addPost(@RequestBody @Validated AddPostDTO dto) {
        Long userId = UserContext.getUser();
        dto.setUserId(userId);
        Long postId = postService.addPost(dto);
        return JSONResult.ok(postId);
    }

    @GetMapping("/myPostList")
    public JSONResult queryMyPostList(Integer page, Integer pageSize) {
        Long userId = UserContext.getUser();
        postService.queryPostList(userId, userId, page, pageSize);
        return JSONResult.ok();
    }


    @PostMapping("/like")
    public JSONResult likePost(@RequestParam Long postId) {
        Long userId = UserContext.getUser();
        postService.likePost(userId, postId);
        return JSONResult.ok();
    }

    @GetMapping("/like/count")
    public JSONResult queryPostLikeCount(@RequestParam Long postId) {
        long likeCount = postService.queryLikeCount(postId);
        return JSONResult.ok(likeCount);
    }

    @GetMapping("/like/check")
    public JSONResult queryCheckPostLike(@RequestParam Long postId) {
        Long userId = UserContext.getUser();
        boolean like = postService.queryCheckPostLike(userId, postId);
        return JSONResult.ok(like);
    }

    @PostMapping("/test/like")
    public JSONResult testLikePost(@RequestParam("userId") Long userId, @RequestParam("postId") Long postId) {
        Long start = System.currentTimeMillis();
        postService.likePost(userId, postId);
        System.out.println("耗时：" + (System.currentTimeMillis() - start));
        return JSONResult.ok();
    }

}

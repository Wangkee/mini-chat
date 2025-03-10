package com.wangkee.controller;

import com.wangkee.dto.FollowshipDTO;
import com.wangkee.dto.BasePageQueryDTO;
import com.wangkee.result.JSONResult;
import com.wangkee.service.FollowService;
import com.wangkee.utils.PagedResult;
import com.wangkee.utils.UserContext;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/follow")
public class FollowController {

    @Resource
    private FollowService followService;

    @GetMapping("/myFollowingList")
    public JSONResult queryFollowingList(@RequestBody @Validated BasePageQueryDTO dto){
        Long userId = UserContext.getUser();
        PagedResult result = followService.queryFollowingList(dto.getPage(), dto.getPageSize(), userId);
        return JSONResult.ok(result);
    }

    @GetMapping("/myFollowerList")
    public JSONResult queryFollowerList(@RequestBody @Validated BasePageQueryDTO dto){
        Long userId = UserContext.getUser();
        PagedResult result = followService.queryFollowerList(dto.getPage(), dto.getPageSize(),userId);
        return JSONResult.ok(result);
    }

    @PostMapping("/add")
    public JSONResult addFollow(@RequestBody @Validated FollowshipDTO dto){
        Long followerId = UserContext.getUser();
        dto.setFollower(followerId);
        followService.addFollow(dto);
        return JSONResult.ok();
    }

    @PostMapping("/delete")
    public JSONResult deleteFollow(@RequestBody @Validated FollowshipDTO dto){
        Long followerId = UserContext.getUser();
        dto.setFollower(followerId);
        followService.deleteFollow(dto);
        return JSONResult.ok();
    }

    @PostMapping("/update")
    public JSONResult updateFollow(@RequestBody @Validated FollowshipDTO dto){
        Long followerId = UserContext.getUser();
        dto.setFollower(followerId);
        followService.updateFollow(dto);
        return JSONResult.ok();
    }

}

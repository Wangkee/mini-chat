package com.wangkee.controller;

import com.wangkee.dto.FollowshipDTO;
import com.wangkee.dto.BasePageQueryDTO;
import com.wangkee.result.JSONResult;
import com.wangkee.service.FollowService;
import com.wangkee.utils.PagedResult;
import com.wangkee.utils.UserContext;
import com.wangkee.vo.FollowListItemVO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/follow")
public class FollowController {

    @Resource
    private FollowService followService;

    @GetMapping("/myFollowingList")
    @Operation(summary = "查询当前用户关注列表接口")
    public JSONResult queryFollowingList(Integer page, Integer pageSize){
        Long userId = UserContext.getUser();
        List<FollowListItemVO> result = followService.queryFollowingList(page, pageSize, userId);
        return JSONResult.ok(result);
    }

    @GetMapping("/myFollowerList")
    @Operation(summary = "查询粉丝列表接口")
    public JSONResult queryFollowerList(Integer page, Integer pageSize){
        Long userId = UserContext.getUser();
        List<FollowListItemVO> result = followService.queryFollowerList(page, pageSize,userId);
        return JSONResult.ok(result);
    }

    @PostMapping("/add")
    @Operation(summary = "新增关注接口")
    public JSONResult addFollow(@RequestBody @Validated FollowshipDTO dto){
        Long followerId = UserContext.getUser();
        dto.setFollower(followerId);
        followService.addFollow(dto);
        return JSONResult.ok();
    }

    @PostMapping("/delete")
    @Operation(summary = "删除关注接口")
    public JSONResult deleteFollow(@RequestBody @Validated FollowshipDTO dto){
        Long followerId = UserContext.getUser();
        dto.setFollower(followerId);
        followService.deleteFollow(dto);
        return JSONResult.ok();
    }

    @PostMapping("/update")
    @Operation(summary = "更新关注接口")
    public JSONResult updateFollow(@RequestBody @Validated FollowshipDTO dto){
        Long followerId = UserContext.getUser();
        dto.setFollower(followerId);
        followService.updateFollow(dto);
        return JSONResult.ok();
    }

}

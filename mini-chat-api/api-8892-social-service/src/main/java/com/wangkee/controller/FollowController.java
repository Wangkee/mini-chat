package com.wangkee.controller;

import com.wangkee.result.JSONResult;
import com.wangkee.service.FollowService;
import com.wangkee.utils.UserContext;
import com.wangkee.vo.FollowListItemVO;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/follow")
public class FollowController {

    @Resource
    private FollowService followService;

    @GetMapping("/myFollowingList")
    public JSONResult queryFollowingList(){
        Long userId = UserContext.getUser();
        List<FollowListItemVO> result = followService.queryFollowingList(userId);
        return JSONResult.ok(result);
    }

    @GetMapping("/myFollowerList")
    public JSONResult queryFollowerList(){
        Long userId = UserContext.getUser();
        List<FollowListItemVO> result = followService.queryFollowerList(userId);
        return JSONResult.ok(result);
    }


}

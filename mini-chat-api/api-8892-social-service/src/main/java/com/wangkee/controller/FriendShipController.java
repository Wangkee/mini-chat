package com.wangkee.controller;

import com.wangkee.bo.DeleteFriendBO;
import com.wangkee.bo.UpdateBlockStatusBO;
import com.wangkee.bo.UpdateFriendRemarkBO;
import com.wangkee.result.JSONResult;
import com.wangkee.service.FriendshipService;
import com.wangkee.utils.UserContext;
import com.wangkee.vo.FriendDetailVO;
import com.wangkee.vo.FriendShipBriefVO;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/friendship")
public class FriendShipController {

    @Resource
    private FriendshipService friendshipService;

    @GetMapping("/friendInfo")
    public JSONResult friendInfo(Long friendId){
        Long userId = UserContext.getUser();
        FriendDetailVO result = friendshipService.queryFriendshipInfo(userId, friendId);
        return JSONResult.ok(result);
    }

    @GetMapping("/friendsList")
    public JSONResult queryFriends(Long userId) {
        List<FriendShipBriefVO> result = friendshipService.queryFriends(userId, false);
        return JSONResult.ok(result);
    }

    @GetMapping("/myFriendsList")
    public JSONResult queryMyFriends() {
        Long userId = UserContext.getUser();
        List<FriendShipBriefVO> result = friendshipService.queryFriends(userId, false);
        return JSONResult.ok(result);
    }

    @GetMapping("/myBlockList")
    public JSONResult queryMyBlockList() {
        Long userId = UserContext.getUser();
        List<FriendShipBriefVO> result = friendshipService.queryFriends(userId, true);
        return JSONResult.ok(result);
    }

    @PostMapping("/updateRemark")
    public JSONResult updateRemark(@RequestBody @Validated UpdateFriendRemarkBO bo){
        Long userId = UserContext.getUser();
        friendshipService.updateRemark(userId, bo.getFriendId(), bo.getNewRemark());
        return JSONResult.ok();
    }

    @PostMapping("/updateBlockStatus")
    public JSONResult updateBlockStatus(@RequestBody @Validated UpdateBlockStatusBO bo){
        Long userId = UserContext.getUser();
        friendshipService.updateBlockStatus(userId, bo.getFriendId(), bo.getIsBlocked());
        return JSONResult.ok();
    }

    @PostMapping("/delete")
    public JSONResult deleteFriend(@RequestBody @Validated DeleteFriendBO bo){
        Long userId = UserContext.getUser();
        friendshipService.deleteFriend(userId, bo.getFriendId());
        return JSONResult.ok();
    }
}

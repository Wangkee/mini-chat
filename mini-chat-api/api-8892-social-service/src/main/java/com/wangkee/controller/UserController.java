package com.wangkee.controller;

import com.wangkee.aspect.loginAspect.LoginApi;
import com.wangkee.bo.UpdateUserInfoBO;
import com.wangkee.result.JSONResult;
import com.wangkee.service.UserService;
import com.wangkee.utils.UserContext;
import com.wangkee.vo.UserVO;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @LoginApi
    @PostMapping("/api/update")
    public JSONResult updateUserInfo(@Validated @RequestBody UpdateUserInfoBO updateUserInfoBO){
        Long userId = UserContext.getUser();
        UserVO userVO = userService.updateUserInfo(updateUserInfoBO, userId);
        return JSONResult.ok(userVO);
    }
}

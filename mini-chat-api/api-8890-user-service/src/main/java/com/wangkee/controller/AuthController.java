package com.wangkee.controller;

import com.wangkee.bo.RegistLoginBO;
import com.wangkee.exceptions.BusinessException;
import com.wangkee.result.ResponseStatusEnum;
import com.wangkee.service.AuthService;
import com.wangkee.utils.UserContext;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.wangkee.result.JSONResult;
import com.wangkee.validate.phone.PhoneNumber;
import com.wangkee.vo.UserVO;

import java.util.Objects;

@Validated
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private AuthService authService;

    @GetMapping("/getSMSCode")
    public JSONResult getSMSCode(@PhoneNumber String mobile){
        authService.getSMSCode(mobile);
        return JSONResult.ok();
    }

    @PostMapping("/register")
    public JSONResult register(@RequestBody @Validated RegistLoginBO bo){
        UserVO usersVO = authService.loginOrRegister(
                bo.getMobile(), bo.getSmsCode(), false
        );
        return JSONResult.ok(usersVO);
    }

    @PostMapping("/login")
    public JSONResult login(@RequestBody @Validated RegistLoginBO bo) {

        UserVO usersVO = authService.loginOrRegister(
                bo.getMobile(), bo.getSmsCode(), true
        );
        return JSONResult.ok(usersVO);
    }

    @PostMapping("/logout")
    public JSONResult logout(){
        Long userId = UserContext.getUser();
        if(Objects.isNull(userId)){
            throw new BusinessException(ResponseStatusEnum.NOT_LOGGED_IN);
        }

        authService.logout(userId);

        return JSONResult.ok();
    }
}

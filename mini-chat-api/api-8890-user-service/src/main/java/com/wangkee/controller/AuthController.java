package com.wangkee.controller;

import com.wangkee.dto.LoginDTO;
import com.wangkee.dto.RegistLoginDTO;
import com.wangkee.exceptions.BusinessException;
import com.wangkee.result.ResponseStatusEnum;
import com.wangkee.service.AuthService;
import com.wangkee.service.strategy.LoginContext;
import com.wangkee.service.strategy.LoginStrategy;
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
@RequestMapping("/api/auth")
public class AuthController {

    @Resource
    private AuthService authService;

    @Resource
    private LoginContext loginContext;

    @GetMapping("/getSMSCode")
    public JSONResult getSMSCode(@PhoneNumber String mobile){
        authService.getSMSCode(mobile);
        return JSONResult.ok();
    }

    @PostMapping("/register")
    public JSONResult register(@RequestBody @Validated RegistLoginDTO bo){
        UserVO usersVO = authService.register(
                bo.getMobile(), bo.getSmsCode()
        );
        return JSONResult.ok(usersVO);
    }


    @PostMapping("/login")
    public JSONResult login(@RequestBody @Validated LoginDTO loginDTO) {
        LoginStrategy loginStrategy = loginContext.getLoginStrategy(loginDTO.getType());
        UserVO usersVO = loginStrategy.login(loginDTO);
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

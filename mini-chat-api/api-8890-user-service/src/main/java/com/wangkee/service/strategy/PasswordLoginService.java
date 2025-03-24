package com.wangkee.service.strategy;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wangkee.constants.BusinessConstants;
import com.wangkee.dto.LoginDTO;
import com.wangkee.exceptions.BusinessException;
import com.wangkee.po.User;
import com.wangkee.result.ResponseStatusEnum;
import com.wangkee.service.impl.AuthServiceImpl;
import com.wangkee.utils.RedisOperator;
import com.wangkee.vo.UserVO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PasswordLoginService implements LoginStrategy{

    @Resource
    private AuthServiceImpl authService;

    @Override
    public UserVO login(LoginDTO dto) {
        String mobile = dto.getPhone();
        String password = dto.getPassword();

        User user = authService.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getMobile, mobile)
                .eq(User::getPassword, password)
        );

        if(Objects.isNull(user)){
            throw new BusinessException(ResponseStatusEnum.USER_PASSWORD_ERROR);
        }

        String accessToken = authService.refreshToken(String.valueOf(user.getId()));

        UserVO usersVO = new UserVO();
        BeanUtils.copyProperties(user, usersVO);
        usersVO.setUserToken(accessToken);

        return usersVO;
    }
}

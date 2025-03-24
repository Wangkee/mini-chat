package com.wangkee.service.strategy;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wangkee.constants.BusinessConstants;
import com.wangkee.dto.LoginDTO;
import com.wangkee.exceptions.BusinessException;
import com.wangkee.mapper.UserMapper;
import com.wangkee.po.User;
import com.wangkee.result.ResponseStatusEnum;
import com.wangkee.service.impl.AuthServiceImpl;
import com.wangkee.utils.RedisOperator;
import com.wangkee.vo.UserVO;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SMSCodeLoginService implements LoginStrategy{

    @Resource
    private RedisOperator redis;

    @Resource
    private AuthServiceImpl authService;

    @Override
    public UserVO login(LoginDTO dto) {
        String phone = dto.getPhone();
        String code = dto.getCode();

        validateSMSCode(phone, code);

        User user = authService.getOne(new LambdaQueryWrapper<User>().eq(User::getMobile, phone));
        if (Objects.isNull(user)) {
            throw new BusinessException(ResponseStatusEnum.USER_NOT_EXIST);
        }

        String accessToken = authService.refreshToken(String.valueOf(user.getId()));

        UserVO usersVO = new UserVO();
        BeanUtils.copyProperties(user, usersVO);
        usersVO.setUserToken(accessToken);

        redis.delete(BusinessConstants.SMS_CODE + phone);
        return usersVO;

    }

    public void validateSMSCode(String phone, String code) {
        String expectedCode = redis.get(BusinessConstants.SMS_CODE + phone);

        if(StringUtils.isBlank(expectedCode) || !expectedCode.equalsIgnoreCase(code)) {
            throw new BusinessException(ResponseStatusEnum.SMS_CODE_ERROR);
        }
    }
}

package com.wangkee.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangkee.constants.AuthConstants;
import com.wangkee.mapper.FollowCountMapper;
import com.wangkee.mapper.PostCountMapper;
import com.wangkee.mapper.UserMapper;
import com.wangkee.po.FollowCount;
import com.wangkee.po.PostCount;
import com.wangkee.service.AuthService;
import com.wangkee.tasks.SMSTask;
import com.wangkee.enums.Gender;
import com.wangkee.exceptions.BusinessException;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.wangkee.po.User;
import com.wangkee.result.ResponseStatusEnum;
import com.wangkee.utils.RedisOperator;
import com.wangkee.vo.UserVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;


@Service
public class AuthServiceImpl extends ServiceImpl<UserMapper, User>
    implements AuthService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private FollowCountMapper followCountMapper;

    @Resource
    private PostCountMapper postCountMapper;

    @Resource
    private SMSTask smsTask;

    @Resource
    private RedisOperator redis;


    /**
     * 发送验证码的业务实现
     * 1. 从redis验证是否能够发送新的验证码（60s只能发送一次）
     * 2. 异步调用短信接口发送验证码
     * 3. 将用户手机号与验证码的对应关系存入redis中
     */
    @Override
    public void getSMSCode(String mobile) {
        String isLimited = redis.get(AuthConstants.SMS_LIMITED + mobile);
        if(!Objects.isNull(isLimited)){
            throw new BusinessException(ResponseStatusEnum.SMS_NEED_WAIT);
        }

        String code = "123456";
        try {
            smsTask.sendSMSInTask(mobile, code);
        }catch (Exception e){
            throw new BusinessException(ResponseStatusEnum.SMS_SEND_FAILED);
        }

        redis.set(AuthConstants.SMS_CODE + mobile, code, AuthConstants.SMS_CODE_TTL);
        redis.set(AuthConstants.SMS_LIMITED + mobile, "", AuthConstants.SMS_LIMITED_TTL);
    }


    @Override
    @Transactional
    public UserVO loginOrRegister(String mobile, String code, boolean isLogin) {
        validateSMSCode(mobile, code);

        User user = getOne(new LambdaQueryWrapper<User>().eq(User::getMobile, mobile));

        if(isLogin){
            if (Objects.isNull(user)) {
                throw new BusinessException(ResponseStatusEnum.USER_NOT_EXIST);
            }
        }else{
            if (!Objects.isNull(user)) {
                throw new BusinessException(ResponseStatusEnum.USER_ALREADY_EXIST);
            }
            user = createUser(mobile);
        }

        String accessToken = refreshToken(String.valueOf(user.getId()));

        UserVO usersVO = new UserVO();
        BeanUtils.copyProperties(user, usersVO);
        usersVO.setUserToken(accessToken);

        redis.delete(AuthConstants.SMS_CODE + mobile);
        return usersVO;
    }

    @Override
    public void logout(Long userId) {
        evictToken(String.valueOf(userId));
    }

    public void validateSMSCode(String mobile, String code) {
        String expectedCode = redis.get(AuthConstants.SMS_CODE + mobile);

        if(StringUtils.isBlank(expectedCode) || !expectedCode.equalsIgnoreCase(code)) {
            throw new BusinessException(ResponseStatusEnum.SMS_CODE_ERROR);
        }
    }

    public String refreshToken(String userId){
        String accessToken = UUID.randomUUID().toString().replace("-", "");
        evictToken(userId);
        redis.set(AuthConstants.TOKEN_TO_USER + accessToken, userId, AuthConstants.TOKEN_TO_USER_TTL);
        redis.set(AuthConstants.USER_TO_TOKEN + userId, accessToken, AuthConstants.USER_TO_TOKEN_TTL);
        return accessToken;
    }

    public void evictToken(String userId){
        String oldToken = redis.get(AuthConstants.USER_TO_TOKEN + userId);
        if(!StringUtils.isBlank(oldToken)){
            redis.delete(AuthConstants.TOKEN_TO_USER + oldToken);
        }
        redis.delete(AuthConstants.USER_TO_TOKEN + userId);
    }

    public User createUser(String mobile) {
        Snowflake snowflake = IdUtil.getSnowflake(0L, 0L);
        Long userId = snowflake.nextId();

        User user = new User();
        user.setId(userId);
        user.setMobile(mobile);
        user.setChatNum(mobile);
        user.setGender(Gender.SECRET.type);
        user.setNickname("用户" + mobile);

        Long currentTime = System.currentTimeMillis();
        user.setUpdatedTime(currentTime);
        user.setCreatedTime(currentTime);

        userMapper.insert(user);

        FollowCount followCount = new FollowCount();
        followCount.setUserId(userId);
        followCountMapper.insert(followCount);

        PostCount postCount = new PostCount();
        postCount.setUserId(userId);
        postCountMapper.insert(postCount);

        return user;
    }
}





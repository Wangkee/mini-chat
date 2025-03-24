package com.wangkee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangkee.constants.BusinessConstants;
import com.wangkee.enums.GenderEnum;
import com.wangkee.exceptions.BusinessException;
import com.wangkee.mapper.FollowCountMapper;
import com.wangkee.mapper.PostCountMapper;
import com.wangkee.mapper.UserMapper;
import com.wangkee.po.FollowCount;
import com.wangkee.po.PostCount;
import com.wangkee.po.User;
import com.wangkee.result.ResponseStatusEnum;
import com.wangkee.service.AuthService;
import com.wangkee.tasks.SMSTask;
import com.wangkee.utils.RedisOperator;
import com.wangkee.utils.SnowFlakeUtil;
import com.wangkee.vo.UserVO;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
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
        String isLimited = redis.get(BusinessConstants.SMS_LIMITED + mobile);
        if(!Objects.isNull(isLimited)){
            throw new BusinessException(ResponseStatusEnum.SMS_NEED_WAIT);
        }

        String code = "123456";
        try {
            smsTask.sendSMSInTask(mobile, code);
        }catch (Exception e){
            throw new BusinessException(ResponseStatusEnum.SMS_SEND_FAILED);
        }

        redis.set(BusinessConstants.SMS_CODE + mobile, code, BusinessConstants.SMS_CODE_TTL);
        redis.set(BusinessConstants.SMS_LIMITED + mobile, "", BusinessConstants.SMS_LIMITED_TTL);
    }


    @Override
    @Transactional
    public UserVO register(String mobile, String code) {
        validateSMSCode(mobile, code);

        User user = getOne(new LambdaQueryWrapper<User>().eq(User::getMobile, mobile));

        if (!Objects.isNull(user)) {
            throw new BusinessException(ResponseStatusEnum.USER_ALREADY_EXIST);
        }
        user = createUser(mobile);

        String accessToken = refreshToken(String.valueOf(user.getId()));

        UserVO usersVO = new UserVO();
        BeanUtils.copyProperties(user, usersVO);
        usersVO.setUserToken(accessToken);

        redis.delete(BusinessConstants.SMS_CODE + mobile);
        return usersVO;
    }

    @Override
    public void logout(Long userId) {
        evictToken(String.valueOf(userId));
    }

    public void validateSMSCode(String mobile, String code) {
        String expectedCode = redis.get(BusinessConstants.SMS_CODE + mobile);

        if(StringUtils.isBlank(expectedCode) || !expectedCode.equalsIgnoreCase(code)) {
            throw new BusinessException(ResponseStatusEnum.SMS_CODE_ERROR);
        }
    }

    public String refreshToken(String userId){
        String accessToken = UUID.randomUUID().toString().replace("-", "");
        evictToken(userId);
        redis.set(BusinessConstants.TOKEN_TO_USER + accessToken, userId, BusinessConstants.TOKEN_TO_USER_TTL);
        redis.set(BusinessConstants.USER_TO_TOKEN + userId, accessToken, BusinessConstants.USER_TO_TOKEN_TTL);
        return accessToken;
    }

    public void evictToken(String userId){
        String oldToken = redis.get(BusinessConstants.USER_TO_TOKEN + userId);
        if(!StringUtils.isBlank(oldToken)){
            redis.delete(BusinessConstants.TOKEN_TO_USER + oldToken);
        }
        redis.delete(BusinessConstants.USER_TO_TOKEN + userId);
    }

    public User createUser(String mobile) {
        Long userId = SnowFlakeUtil.nextId();

        User user = new User();
        user.setId(userId);
        user.setMobile(mobile);
        user.setChatNum(mobile);
        user.setGender(GenderEnum.SECRET.type);
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

    public User createMockUser(String mobile) {

        User user = new User();
        user.setId(Long.valueOf(mobile));
        user.setMobile(mobile);
        user.setChatNum(mobile);
        user.setGender(GenderEnum.SECRET.type);
        user.setNickname("用户" + mobile);

        Long currentTime = System.currentTimeMillis();
        user.setUpdatedTime(currentTime);
        user.setCreatedTime(currentTime);

        userMapper.insert(user);

        FollowCount followCount = new FollowCount();
        followCount.setUserId(Long.valueOf(mobile));
        followCountMapper.insert(followCount);

        PostCount postCount = new PostCount();
        postCount.setUserId(Long.valueOf(mobile));
        postCountMapper.insert(postCount);

        return user;
    }
}





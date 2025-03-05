package com.wangkee.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangkee.bo.UpdateUserInfoBO;
import com.wangkee.enums.Gender;
import com.wangkee.exceptions.BusinessException;
import com.wangkee.mapper.UserMapper;
import com.wangkee.po.User;
import com.wangkee.result.ResponseStatusEnum;
import com.wangkee.service.UserService;
import com.wangkee.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 更新用户信息
     */
    @Override
    public UserVO updateUserInfo(UpdateUserInfoBO updateUserInfoBO, Long userId) {
        User user = getById(userId);
        updateAttributes(user, updateUserInfoBO);
        user.setUpdatedTime(System.currentTimeMillis());

        updateById(user);

        UserVO usersVO = new UserVO();
        BeanUtils.copyProperties(user, usersVO);
        return usersVO;
    }


    private void updateAttributes(User user, UpdateUserInfoBO updateUserInfoBO)  {
        if (updateUserInfoBO == null || user == null) {
            return;
        }

        try {
            // 获取UpdateUserInfoBO和User类的所有字段
            Field[] boFields = updateUserInfoBO.getClass().getDeclaredFields();
            Field[] userFields = user.getClass().getDeclaredFields();

            // 遍历UpdateUserInfoBO中的字段
            for (Field boField : boFields) {
                boField.setAccessible(true);
                Object boValue = boField.get(updateUserInfoBO);

                // 如果字段值不为null
                if (boValue != null) {
                    for (Field userField : userFields) {
                        userField.setAccessible(true);

                        if (boField.getName().equals(userField.getName())) {
                            userField.set(user, boValue);
                            break;
                        }
                    }
                }
            }
        }catch (IllegalAccessException illegalAccessException){
            throw new BusinessException(ResponseStatusEnum.FAILED);
        }
    }
}

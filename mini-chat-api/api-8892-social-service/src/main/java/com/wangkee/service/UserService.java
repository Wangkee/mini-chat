package com.wangkee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangkee.dto.UpdateUserInfoDTO;
import com.wangkee.po.User;
import com.wangkee.vo.UserVO;

public interface UserService extends IService<User> {
    UserVO updateUserInfo(UpdateUserInfoDTO updateUserInfoDTO, Long userId);
}

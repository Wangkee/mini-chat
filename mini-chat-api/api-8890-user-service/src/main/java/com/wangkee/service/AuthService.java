package com.wangkee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangkee.po.User;
import com.wangkee.vo.UserVO;


public interface AuthService extends IService<User> {

    void getSMSCode(String mobile);

    UserVO register(String mobile, String code);

    void logout(Long userId);
}

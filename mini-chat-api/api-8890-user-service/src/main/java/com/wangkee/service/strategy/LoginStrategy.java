package com.wangkee.service.strategy;

import com.wangkee.dto.LoginDTO;
import com.wangkee.vo.UserVO;

public interface LoginStrategy {
    UserVO login(LoginDTO dto);
}

package com.wangkee.service.strategy;

import com.wangkee.exceptions.BusinessException;
import com.wangkee.result.ResponseStatusEnum;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LoginContext {
    private final Map<Integer, LoginStrategy> map = new HashMap<>();

    public LoginContext() {
        map.put(1, new PasswordLoginService());
        map.put(2, new SMSCodeLoginService());
    }

    public LoginStrategy getLoginStrategy(Integer type) {
        if(!map.containsKey(type)){
            throw new BusinessException(ResponseStatusEnum.LOGIN_TYPE_ERROR);
        }

        return map.get(type);
     }
}

package com.wangkee.config;

import com.wangkee.constants.AuthConstants;
import com.wangkee.utils.RedisOperator;
import com.wangkee.utils.UserContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig implements RequestInterceptor {

    @Resource
    private RedisOperator redisOperator;

    @Override
    public void apply(RequestTemplate template) {
        Long userId = UserContext.getUser();
        String token = redisOperator.get(AuthConstants.USER_TO_TOKEN + userId);
        template.header("authorization", token);
    }
}
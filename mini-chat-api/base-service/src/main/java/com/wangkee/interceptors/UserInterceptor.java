package com.wangkee.interceptors;

import com.wangkee.constants.AuthConstants;
import com.wangkee.utils.RedisOperator;
import com.wangkee.utils.UserContext;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Resource
    private RedisOperator redisOperator;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String token = request.getHeader("authorization");
        if(StringUtils.isBlank(token)){
            return true;
        }

        String userId = redisOperator.get(AuthConstants.TOKEN_TO_USER + token);
        if(StringUtils.isBlank(userId)){
            return true;
        }

        UserContext.setUser(Long.valueOf(userId));

        return true; // 继续处理请求
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 清理 ThreadLocal 防止内存泄漏
        UserContext.clear();
    }
}

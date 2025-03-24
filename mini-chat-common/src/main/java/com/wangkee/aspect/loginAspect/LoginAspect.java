package com.wangkee.aspect.loginAspect;

import cn.hutool.core.util.StrUtil;
import com.wangkee.constants.BusinessConstants;
import com.wangkee.exceptions.BusinessException;
import com.wangkee.result.ResponseStatusEnum;
import com.wangkee.utils.RedisOperator;
import com.wangkee.utils.UserContext;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class LoginAspect {
    public static final String LOGIN_AUTH_REQUEST_HEADER_NAME = "authorization";

    public static final String POINT_CUT =
            "@annotation(com.wangkee.aspect.loginAspect.LoginApi) || " +
                    "@within(com.wangkee.aspect.loginAspect.LoginApi)";


    @Resource
    private RedisOperator redisOperator;

    @Pointcut(value = POINT_CUT)
    public void loginAuth(){

    }

    @Around("loginAuth()")
    public Object loginAuthAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String requestURI = request.getRequestURI();

        if(!checkAndSaveUserId(request)){
            log.warn("请求拦截成功，URI为{}，检测到用户未登录，将跳转至登录页面", requestURI);
            throw new BusinessException(ResponseStatusEnum.NOT_LOGGED_IN);
        }else{
            log.info("请求拦截成功，URI为{}，用户已登录，请求通过", requestURI);
            return proceedingJoinPoint.proceed();
        }
    }

    private boolean checkAndSaveUserId(HttpServletRequest request) {
        String token = request.getHeader(LOGIN_AUTH_REQUEST_HEADER_NAME);
        if(StrUtil.isBlank(token)){
            return false;
        }

        String userIdStr = redisOperator.get(BusinessConstants.TOKEN_TO_USER + token);
        if(StrUtil.isBlank(userIdStr)){
            return false;
        }
        Long userId = Long.parseLong(userIdStr);
        UserContext.setUser(userId);
        return true;
    }
}
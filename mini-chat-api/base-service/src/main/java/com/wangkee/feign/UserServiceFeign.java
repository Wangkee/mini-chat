package com.wangkee.feign;

import com.wangkee.dto.UpdateUserInfoDTO;
import com.wangkee.config.FeignConfig;
import com.wangkee.result.JSONResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "api-8890-user-service", configuration = FeignConfig.class)
public interface UserServiceFeign {

    @PostMapping("/user/update")
    JSONResult updateUserInfo(@Validated @RequestBody UpdateUserInfoDTO updateUserInfoDTO);
}

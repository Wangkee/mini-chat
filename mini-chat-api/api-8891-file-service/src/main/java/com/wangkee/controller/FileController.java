package com.wangkee.controller;

import com.wangkee.aspect.loginAspect.LoginApi;
import com.wangkee.bo.UpdateUserInfoBO;
import com.wangkee.feign.UserServiceFeign;
import com.wangkee.result.JSONResult;
import com.wangkee.util.CosOperator;
import com.wangkee.utils.UserContext;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private CosOperator cosOperator;

    @Resource
    private UserServiceFeign userServiceFeign;

    @LoginApi
    @PostMapping("/upload/avatar")
    public JSONResult uploadAvatar(@RequestParam("file") MultipartFile file){
        Long userId = UserContext.getUser();
        String fileUrl = cosOperator.uploadFile(file, userId, "avatar");

        UpdateUserInfoBO updateUserInfoBO = new UpdateUserInfoBO();
        updateUserInfoBO.setAvatar(fileUrl);

        return userServiceFeign.updateUserInfo(updateUserInfoBO);
    }

    @LoginApi
    @PostMapping("/upload/homepageImage")
    public JSONResult uploadHomepageImage(@RequestParam("file") MultipartFile file){
        Long userId = UserContext.getUser();
        String fileUrl = cosOperator.uploadFile(file, userId, "homepageImg");

        UpdateUserInfoBO updateUserInfoBO = new UpdateUserInfoBO();
        updateUserInfoBO.setHomepageImg(fileUrl);

        return userServiceFeign.updateUserInfo(updateUserInfoBO);
    }
}

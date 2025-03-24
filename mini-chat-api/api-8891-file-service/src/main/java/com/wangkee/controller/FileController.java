package com.wangkee.controller;

import com.wangkee.aspect.loginAspect.LoginApi;
import com.wangkee.dto.UpdateUserInfoDTO;
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

        UpdateUserInfoDTO updateUserInfoDTO = new UpdateUserInfoDTO();
        updateUserInfoDTO.setAvatar(fileUrl);

        return userServiceFeign.updateUserInfo(updateUserInfoDTO);
    }

    @LoginApi
    @PostMapping("/upload/homepageImage")
    public JSONResult uploadHomepageImage(@RequestParam("file") MultipartFile file){
        Long userId = UserContext.getUser();
        String fileUrl = cosOperator.uploadFile(file, userId, "homepageImg");

        UpdateUserInfoDTO updateUserInfoDTO = new UpdateUserInfoDTO();
        updateUserInfoDTO.setHomepageImg(fileUrl);

        return userServiceFeign.updateUserInfo(updateUserInfoDTO);
    }

    @LoginApi
    @PostMapping("/upload/postImage")
    public JSONResult uploadPostImage(@RequestParam("file") MultipartFile file){
        Long userId = UserContext.getUser();
        String fileUrl = cosOperator.uploadFile(file, userId, "postImg");

        return JSONResult.ok(fileUrl);
    }
}

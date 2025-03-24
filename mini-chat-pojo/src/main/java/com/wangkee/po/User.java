package com.wangkee.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wangkee.utils.LocalDateUtils;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户表
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@TableName(value ="user")
public class User implements Serializable {

    /**
     * 用户id
     */
    private Long id;

    /**
     * chat号
     */
    private String chatNum;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别，1:男 0:女 2:保密
     */
    private Integer gender;

    /**
     * 密码
     */
    private String password;

    /**
     * 生日
     */
    @JsonFormat(pattern = LocalDateUtils.DATE_PATTERN)
    private LocalDate birthday;

    /**
     * 用户头像 url地址
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 学校
     */
    private String school;

    /**
     * 居住地
     */
    private String location;

    /**
     * 用户主页背景图
     */
    private String homepageImg;

    /**
     * 创建时间
     */
    private Long createdTime;

    /**
     * 更新时间
     */
    private Long  updatedTime;
}

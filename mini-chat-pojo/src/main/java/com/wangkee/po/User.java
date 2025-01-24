package com.wangkee.po;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
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
     * 生日
     */
    private Date birthday;
    /**
     * 用户头像
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
     * 主页背景图
     */
    private String homepageImg;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新时间
     */
    private Date updatedTime;

}

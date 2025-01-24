package com.wangkee.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    private Long id;
    private String chatNum;
    private String mobile;
    private String nickname;
    private Integer gender;
    private Date birthday;
    private String avatar;
    private String email;
    private String signature;
    private String school;
    private String location;
    private String homepageImg;
    private Date createdTime;
    private Date updatedTime;

    private String userToken;
}

package com.wangkee.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.wangkee.utils.LocalDateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
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

    @JsonFormat(pattern = LocalDateUtils.DATE_PATTERN)
    private Date birthday;

    private String avatar;

    private String email;

    private String signature;

    private String school;

    private String location;

    private String homepageImg;

    private String userToken;
}

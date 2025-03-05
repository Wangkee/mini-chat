package com.wangkee.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.wangkee.utils.LocalDateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FriendDetailVO {
    private Long friendShipId;
    private Long userId;
    private Long friendId;
    private String chatNum;
    private String nickname;
    private Integer gender;
    @JsonFormat(pattern = LocalDateUtils.DATE_PATTERN)
    private LocalDate birthday;
    private String avatar;
    private String signature;
    private String school;
    private String location;
    private String homepageImg;
    private String remark;
    private Boolean chatOnly;
    private Boolean blocked;
    private Integer friendshipDays;
}

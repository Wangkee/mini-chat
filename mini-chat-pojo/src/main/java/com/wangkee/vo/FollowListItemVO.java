package com.wangkee.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FollowListItemVO {
    Long userId;
    String nickname;
    String remark;
    String avatar;
    String signature;
    Integer followStatus;
    Integer followerCount;
    Integer postCount;
}

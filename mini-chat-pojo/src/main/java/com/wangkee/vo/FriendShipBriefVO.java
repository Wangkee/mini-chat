package com.wangkee.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FriendShipBriefVO {
    private Long friendShipId;
    private Long userId;
    private Long friendId;
    private String friendNickname;
    private String friendAvatar;
    private String remark;
    private Boolean chatOnly;
    private Boolean blocked;
}

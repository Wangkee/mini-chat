package com.wangkee.enums;

import lombok.Getter;

@Getter
public enum FollowStatusEnum {
    NOT_FOLLOWED(0, "未关注"),
    FOLLOWING(1, "已关注"),
    MUTUAL(2, "相互关注");

    private final int code;
    private final String description;

    FollowStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
package com.wangkee.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum VisibilityEnum {
    PRIVATE(0, "仅自己可见"),
    PUBLIC(1, "公开"),
    FOLLOWERS_ONLY(2, "仅粉丝可见"),
    PARTIAL_VISIBLE(3, "部分用户可见"),
    PARTIAL_INVISIBLE(4, "部分用户不可见");

    private final int code;
    private final String description;

    VisibilityEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
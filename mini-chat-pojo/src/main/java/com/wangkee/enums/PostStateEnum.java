package com.wangkee.enums;

import lombok.Getter;

@Getter
public enum PostStateEnum {
    NORMAL(0, "正常"),
    PENDING(1, "审核中"),
    DELETED(2, "已删除");

    private final int code;
    private final String description;

    PostStateEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}

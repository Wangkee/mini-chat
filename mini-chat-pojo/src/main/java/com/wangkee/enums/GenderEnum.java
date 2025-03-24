package com.wangkee.enums;

import lombok.Getter;

@Getter
public enum GenderEnum {
    FEMALE(0, "女"),
    MALE(1, "男"),
    SECRET(2, "保密");

    public final Integer type;
    public final String value;

    GenderEnum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}

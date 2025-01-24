package com.wangkee.enums;

public enum Gender {
    FEMALE(0, "女"),
    MALE(1, "男"),
    SECRET(2, "保密");

    public final Integer type;
    public final String value;

    Gender(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}

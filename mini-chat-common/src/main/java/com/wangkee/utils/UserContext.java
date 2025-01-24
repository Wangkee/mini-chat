package com.wangkee.utils;

public class UserContext {
    private static final ThreadLocal<Long> userThreadLocal = new ThreadLocal<>();

    // 设置用户信息
    public static void setUser(Long userId) {
        userThreadLocal.set(userId);
    }

    // 获取用户信息
    public static Long getUser() {
        return userThreadLocal.get();
    }

    // 清除用户信息（防止内存泄漏）
    public static void clear() {
        userThreadLocal.remove();
    }
}

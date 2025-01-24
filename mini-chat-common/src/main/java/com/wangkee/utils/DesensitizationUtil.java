package com.wangkee.utils;

/**
 * 通用脱敏工具类
 * 可用于：
 *      用户名
 *      手机号
 *      邮箱
 *      地址等
 */
public class DesensitizationUtil {

    private static final int SIZE = 6;
    private static final String SYMBOL = "*";

    /**
     * 通用脱敏方法
     */
    public static String commonDisplay(String value) {
        // 输入检查
        if (value == null || value.isEmpty()) {
            return value;
        }

        int len = value.length();
        if (len <= 2) {
            // 长度小于等于2的特殊处理
            return len == 1 ? SYMBOL : SYMBOL + value.charAt(len - 1);
        }

        StringBuilder stringBuilder = new StringBuilder();
        int maskLen = Math.min(SIZE, len - 2); // 要脱敏的字符数量
        int startMask = (len - maskLen) / 2;   // 脱敏部分起始位置

        // 拼接结果：前缀 + 脱敏部分 + 后缀
        stringBuilder.append(value, 0, startMask)
                .append(SYMBOL.repeat(maskLen))
                .append(value, startMask + maskLen, len);

        return stringBuilder.toString();
    }
}

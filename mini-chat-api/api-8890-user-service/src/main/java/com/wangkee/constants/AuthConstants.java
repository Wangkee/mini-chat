package com.wangkee.constants;

public class AuthConstants {
    /**
     * 存储手机号以及对应的验证码
     * key      : sms:code:18999999999
     * value    : 829411
     */
    public static final String SMS_CODE = "sms:code:";
    public static final long SMS_CODE_TTL = 10 * 60;

    /**
     * 存储 1分钟内已经发送过验证码的手机号
     * key      : sms:limited:18999999999
     * value    :
     */
    public static final String SMS_LIMITED = "sms:limited:";
    public static final long SMS_LIMITED_TTL = 60;

    public static final String TOKEN_TO_USER = "token_to_user:";
    public static final long TOKEN_TO_USER_TTL = 30 * 24 * 60 * 60;

    public static final String USER_TO_TOKEN = "user_to_token:";
    public static final long USER_TO_TOKEN_TTL = 30 * 24 * 60 * 60;
}

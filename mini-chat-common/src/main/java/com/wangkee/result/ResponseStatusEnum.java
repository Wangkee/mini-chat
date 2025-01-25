package com.wangkee.result;

/**
 * 响应结果枚举，用于提供给GraceJSONResult返回给前端的
 * 本枚举类中包含了很多的不同的状态码供使用，可以自定义
 * 便于更优雅的对状态码进行管理，一目了然
 */
public enum ResponseStatusEnum {

    SUCCESS(200, true, "操作成功！"),
    FAILED(500, false, "操作失败！"),

    NOT_LOGGED_IN(501, false,"请登录后再继续操作！"),
    INVALID_TOKEN(502, false, "会话失效，请重新登录！"),
    PERMISSION_DENIED(503,false,"您的权限不足，无法继续操作！"),

    USER_ALREADY_EXIST(5041,false,"该用户已存在，不可重复注册！"),
    USER_NOT_EXIST(5042,false,"该用户不存在，请前往注册！"),
    USER_FROZEN(5043,false,"用户已被冻结，请联系管理员！"),

    SMS_SEND_FAILED(5051, false, "短信发送失败，请稍后重试！"),
    SMS_CODE_ERROR(5052,false,"验证码过期或不匹配！"),
    SMS_NEED_WAIT(5053,false,"短信发送太快啦~请稍后再试！"),


    FILE_SYSTEM_ERROR(510, false, "文件系统异常"),
    FILE_UPLOAD_NULL(510,false,"文件不能为空，请选择一个文件再上传！"),
    FILE_UPLOAD_FAILED(511,false,"文件上传失败, 请稍后再试！"),
    FILE_WRONG_FORMAT(512,false,"文件格式不支持！"),
    FILE_NOT_EXIST_ERROR(514,false,"你所查看的文件不存在！");

    // 响应业务状态
    private final Integer status;
    // 调用是否成功
    private final Boolean success;
    // 响应消息，可以为成功或者失败的消息
    private final String msg;

    ResponseStatusEnum(Integer status, Boolean success, String msg) {
        this.status = status;
        this.success = success;
        this.msg = msg;
    }

    public Integer status() {
        return status;
    }
    public Boolean success() {
        return success;
    }
    public String msg() {
        return msg;
    }
}

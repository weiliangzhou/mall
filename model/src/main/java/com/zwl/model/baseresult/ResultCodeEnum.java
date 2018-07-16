package com.zwl.model.baseresult;

public enum ResultCodeEnum {
    //成功
    SUCCESS("200", "成功"),

    // 操作失败
    FAIL("205", "操作失败"),
    /**
     * 发生异常
     */
    EXCEPTION("401", "发生异常"),

    /**
     * 系统错误
     */
    SYS_ERROR("402", "系统错误"),

    /**
     * 参数错误
     */
    PARAMS_ERROR("403", "参数错误 "),
    //字段传入为空
    PARAMS_IS_NULL("4031", "参数为空"),
    //字段传入重复
    PARAMS_IS_REPEAT("4032", "参数值已存在"),

    TOKEN_ERROR("900","token无效");
    private String val;
    private String msg;

    public String val() {
        return val;
    }

    public String msg() {
        return msg;
    }

    ResultCodeEnum(String val, String msg) {
        this.val = val;
        this.msg = msg;
    }
}

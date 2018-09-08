package com.zwl.model.constant;

/**
 * 用户注册的方式
 */
public enum UserRegisterType {

    /**
     * 1  微信授权
     */
    WECHAT_ACCREDIT(1),
    /**
     * 2  线下导入
     */
    OFFLINE_IMPORT(2),
    /**
     * 3  手机注册
     */
    PHONE_REGISTER(3);

    UserRegisterType(Integer value) {
        this.value = value;
    }

    private Integer value;

    public Integer getValue() {
        return value;
    }
}

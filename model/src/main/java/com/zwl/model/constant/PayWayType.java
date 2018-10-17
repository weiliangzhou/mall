package com.zwl.model.constant;

import lombok.Data;

/**
 * 用户提现方式
 */
public enum PayWayType {

    /**
     * 微信提现
     */
    WECHAT(1),

    /**
     * 提现至余额
     */
    BALANCE(2),
    /**
     * 提现至银行卡
     */
    BANKCARD(3);
    private int code;


    PayWayType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static PayWayType getPayWayTypeByCode(int code) {
        for (PayWayType payWayType : PayWayType.values()) {
            if (payWayType.getCode() == code) {
                return payWayType;
            }
        }
        return null;
    }

}

package com.zwl.model.vo;

import com.zwl.model.baseresult.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信返回错误消息
 */
public class WxResultErrorMessage extends Result {

    public WxResultErrorMessage(Integer errorCode, String msg) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("errorcode", errorCode);
        resultMap.put("errormsg", msg);
        this.setData(resultMap);
    }
}

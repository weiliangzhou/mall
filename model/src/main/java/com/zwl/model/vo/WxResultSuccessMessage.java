package com.zwl.model.vo;

import com.zwl.model.baseresult.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信账号授权登录成功返回
 */
public class WxResultSuccessMessage extends Result {

    public WxResultSuccessMessage(String userId , String gzhOpenId) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userId", userId);
        resultMap.put("gzhopenid", gzhOpenId);
        this.setData(resultMap);
    }
}

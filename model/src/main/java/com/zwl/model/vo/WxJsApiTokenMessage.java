package com.zwl.model.vo;

import com.zwl.model.baseresult.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信公众号 JSapi调用权限返回信息
 */
public class WxJsApiTokenMessage extends Result {
    /**
     * @param nonceStr  随机字符串
     * @param ticket    微信返回 js ticket
     * @param timestamp 当前时间
     * @param url       链接
     * @param signature 签名(sha1)
     */
    public WxJsApiTokenMessage(String nonceStr, String ticket, String timestamp, String url, String signature) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("nonceStr", nonceStr);
        resultMap.put("jsapi_ticket", ticket);
        resultMap.put("timestamp", timestamp);
        resultMap.put("url", url);
        resultMap.put("signature", signature);
        this.setData(resultMap);
    }
}

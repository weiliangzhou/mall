package com.zwl.model.vo;

import com.zwl.model.baseresult.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信公众号 JSapi调用权限返回信息
 */
public class WxJsApiTokenMessage extends Result {
    //JSAPI权限列表
    public static List<String> jsApiLists = new ArrayList<>();

    /**
     * @param nonceStr  随机字符串
     * @param appId     公众号APPID
     * @param timestamp 当前时间
     * @param url       链接
     * @param signature 签名(sha1)
     */
    public WxJsApiTokenMessage(String nonceStr, String appId, String timestamp, String url, String signature) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("nonceStr", nonceStr);
        resultMap.put("appId", appId);
        resultMap.put("timestamp", timestamp);
        resultMap.put("url", url);
        resultMap.put("signature", signature);
        resultMap.put("jsApiList", jsApiLists);
        this.setData(resultMap);
    }

    static {
        jsApiLists.add("onMenuShareTimeline");
        jsApiLists.add("onMenuShareAppMessage");
        jsApiLists.add("onMenuShareQQ");
        jsApiLists.add("onMenuShareWeibo");
        jsApiLists.add("onMenuShareQZone");
        jsApiLists.add("startRecord");
        jsApiLists.add("stopRecord");
        jsApiLists.add("onVoiceRecordEnd");
        jsApiLists.add("playVoice");
        jsApiLists.add("pauseVoice");
        jsApiLists.add("stopVoice");
        jsApiLists.add("onVoicePlayEnd");
        jsApiLists.add("uploadVoice");
        jsApiLists.add("downloadVoice");
        jsApiLists.add("chooseImage");
        jsApiLists.add("previewImage");
        jsApiLists.add("uploadImage");
        jsApiLists.add("downloadImage");
        jsApiLists.add("translateVoice");
        jsApiLists.add("getNetworkType");
        jsApiLists.add("openLocation");
        jsApiLists.add("getLocation");
        jsApiLists.add("hideOptionMenu");
        jsApiLists.add("showOptionMenu");
        jsApiLists.add("hideMenuItems");
        jsApiLists.add("showMenuItems");
        jsApiLists.add("hideAllNonBaseMenuItem");
        jsApiLists.add("showAllNonBaseMenuItem");
        jsApiLists.add("closeWindow");
        jsApiLists.add("scanQRCode");
        jsApiLists.add("chooseWXPay");
        jsApiLists.add("openProductSpecificView");
        jsApiLists.add("addCard");
        jsApiLists.add("chooseCard");
        jsApiLists.add("openCard");
    }

}

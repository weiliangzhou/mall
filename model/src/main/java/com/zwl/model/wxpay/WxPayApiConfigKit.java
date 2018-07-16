package com.zwl.model.wxpay;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 二师兄超级帅
 * @Title: WxPayApiConfigKit
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/920:34
 */
public class WxPayApiConfigKit {
    private static final ThreadLocal<String> TL = new ThreadLocal();
    private static final Map<String, WxPayH5> CFG_MAP = new ConcurrentHashMap();

    public static WxPayH5 putApiConfig(WxPayH5 wxPayH5) {
        if (CFG_MAP.size() == 0) {
            CFG_MAP.put("_default_zwl_key_", wxPayH5);
        }

        return (WxPayH5) CFG_MAP.put(wxPayH5.getAppId(), wxPayH5);
    }

    public static WxPayH5 setThreadLocalWxPayApiConfig(WxPayH5 wxPayH5) {
        return putApiConfig(wxPayH5);
    }

    public static String getAppId() {
        String appId = TL.get();
        if (StrKit.isBlank(appId)) {
            appId = (CFG_MAP.get("_default_zwl_key_")).getAppId();
        }

        return appId;
    }

    public static WxPayH5 getWxPayApiConfig() {
        String appId = getAppId();
        return getApiConfig(appId);
    }

    public static WxPayH5 getApiConfig(String appId) {
        WxPayH5 cfg = CFG_MAP.get(appId);
        if (cfg == null) {
            throw new IllegalStateException("需事先调用 WxPayApiConfigKit.putApiConfig(wxPayApiConfig) 将 appId对应的 WxPayApiConfig 对象存入，才可以使用 WxPayApiConfigKit.getWxPayApiConfig() 的系列方法");
        } else {
            return cfg;
        }
    }
}

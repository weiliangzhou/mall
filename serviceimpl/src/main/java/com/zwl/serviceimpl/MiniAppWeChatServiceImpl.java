package com.zwl.serviceimpl;

import com.zwl.service.MiniAppWeChatService;
import com.zwl.util.HttpsUtils;
import org.springframework.stereotype.Service;

@Service
public class MiniAppWeChatServiceImpl implements MiniAppWeChatService {
    @Override
    public String authorizationCode(String jscode, String appId, String appSecret) {
        // 获取接口URL
        String args="?appid="+appId+"&"+"secret="+appSecret+"&"+"js_code="+jscode+"&grant_type=authorization_code";
        String url="https://api.weixin.qq.com/sns/jscode2session"+args;
        String resultStr=HttpsUtils.sendGet(url,null);
        return resultStr;
    }
}

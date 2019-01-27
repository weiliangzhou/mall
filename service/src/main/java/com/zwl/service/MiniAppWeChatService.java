package com.zwl.service;

import org.springframework.stereotype.Service;

/**
 * 微信小程序调用微信接口
 */
@Service
public interface MiniAppWeChatService {
    String authorizationCode(String jscode, String appId, String appSecret);
}

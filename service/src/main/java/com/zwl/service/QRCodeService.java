package com.zwl.service;

/**
 * Created by 二师兄超级帅 on 2018/8/5.
 */
public interface QRCodeService {
    String getQRCode(String userId, String page, String accessToken);
}

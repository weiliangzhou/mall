package com.zwl.service;

/**
 * @author 二师兄超级帅
 * @Title: MessageSenderService
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1413:57
 */
public interface MsgSenderService {
    public void sendCode(String phone);
    public boolean checkCode(String phone,String code);
}

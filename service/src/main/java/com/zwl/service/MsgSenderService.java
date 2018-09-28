package com.zwl.service;

import com.zwl.model.baseresult.Result;

/**
 * @author 二师兄超级帅
 * @Title: MessageSenderService
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1413:57
 */
public interface MsgSenderService {
    public void sendCode(String phone, String busCode);
    public void sendMsg(String phone,String msg);
    public boolean checkCode(String phone,String code,String busCode);
}

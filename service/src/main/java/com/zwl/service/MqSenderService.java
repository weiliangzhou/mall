package com.zwl.service;


import javax.jms.Destination;

/**
 * @author 二师兄超级帅
 * @Title: MqManager 消息队列管理器
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/8/1314:42
 */
public interface MqSenderService {
    public void sendMq(Destination destinationName, final String message);
}

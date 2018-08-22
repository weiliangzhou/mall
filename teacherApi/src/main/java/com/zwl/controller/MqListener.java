package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.zwl.util.HttpsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author 二师兄超级帅
 * @Title: MqListener
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/8/1314:58
 */
@Component
@Slf4j
public class MqListener {
    // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
    @JmsListener(destination = "kctz.queue")
    public void receiveQueue(String text) {
        log.info("Consumer收到的报文为:" + text);
        JSONObject jsonObject=JSONObject.parseObject(text);
        String msgurl=jsonObject.getString("msgurl");
        String params=jsonObject.getString("data");
        String result = HttpsUtils.sendPost(msgurl, params);
        log.info("发送微信模板结果" + result);
//        {
//            "errcode":0,
//                "errmsg":"ok",
//                "msgid":200228332
//        }

    }
}

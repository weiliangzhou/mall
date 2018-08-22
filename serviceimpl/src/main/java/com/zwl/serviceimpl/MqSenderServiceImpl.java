package com.zwl.serviceimpl;

import com.zwl.service.MqSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;

/**
 * @author 二师兄超级帅
 * @Title: MqSenderServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/8/1314:46
 */
@Service
public class MqSenderServiceImpl implements MqSenderService {
    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void sendMq(Destination destinationName, String message) {
        jmsTemplate.convertAndSend(destinationName, message);

    }
}

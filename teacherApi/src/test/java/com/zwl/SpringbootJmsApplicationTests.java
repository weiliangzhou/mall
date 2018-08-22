package com.zwl;

import com.zwl.model.po.User;
import com.zwl.service.MqSenderService;
import com.zwl.service.UserService;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.Destination;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootJmsApplicationTests {

    @Autowired
    private MqSenderService producer;
    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() throws InterruptedException {
        Destination destination = new ActiveMQQueue("kctz.queue");

        for (int i = 0; i < 100; i++) {
            producer.sendMq(destination, "myname is 二师兄超级帅!!!");
        }
    }

    @Test
    public void testParentList() {
        List userList = findReferrer("de3c5bf3d8004a06b5bb22c94f63c7e4");
        System.out.println(userList);

    }

    private List findReferrer(String userId) {
        List<String> userIdList = new ArrayList<>();
        User refferrer = userService.getByUserId(userId);
        while (refferrer != null) {
            userId = refferrer.getReferrer();
            if (StringUtils.isNotBlank(userId))
                userIdList.add(userId);
            refferrer = userService.getByUserId(userId);

        }
        return userIdList;


    }

}
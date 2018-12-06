package com.zwl;

import com.zwl.model.po.User;
import com.zwl.service.UserService;
import com.zwl.util.UUIDUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebApplicationTests {
    @Autowired
    UserService userService;

    @Test
    public void createUser() {
//        Set<String> phoneList = new HashSet<>();
        List<String> phoneList = new ArrayList<>();
        phoneList.add("13357187857");
        phoneList.add("13599541354");
        phoneList.add("13795158710");
        phoneList.add("13646257206");
        phoneList.add("18686804230");
        phoneList.add("18963787451");
        phoneList.add("15959510935");
        phoneList.add("18766395180");
        phoneList.add("18137144888");
        phoneList.add("18852383588");
        phoneList.add("18762096000");
        phoneList.add("18615798838");
        phoneList.add("13750966639");
        phoneList.add("15502639375");
        phoneList.add("18651812785");
        phoneList.add("13511439268");
        phoneList.add("18158798567");
        phoneList.add("18384121488");
        phoneList.add("18622637868");
        phoneList.add("17755189207");
        phoneList.add("18665996771");
        phoneList.add("13510024154");
        phoneList.add("13921322391");
        phoneList.add("13998188880");
        phoneList.add("15633929999");
        phoneList.add("15252255819");
        phoneList.add("13512570521");
        phoneList.add("13831785148");
        phoneList.add("15036096628");
        phoneList.add("13958898969");
        phoneList.add("13808879813");
        phoneList.add("13983049666");
        phoneList.add("13819964196");
        phoneList.add("13535403504");
        phoneList.add("13853237182");
        phoneList.add("15678827742");
        phoneList.add("13732428999");
        phoneList.add("18167162122");
        phoneList.add("18636933387");
        phoneList.add("18909027679");
        phoneList.add("18367749022");
        phoneList.add("15161616605");
        for (String phone : phoneList) {
            Map userMap = new HashMap();
            //提供手机号查询User 不存在 则insert
            //否则 update
            User qUser = new User();
            qUser.setRegisterMobile(phone);
            User oneByParams = userService.getOneByParams(qUser);
            if (oneByParams == null) {
                String userId = UUIDUtil.getUUID32();
                System.out.println("INSERT INTO `ss_user` (`user_id`,`wechat_openid`,`wechat_union_id`,`gzh_openid`,`merchant_id`,`register_from`,`register_mobile`,`real_name`,`logo_url`,`referrer`,`member_level`,`level_name`,`is_buy`,`expires_time`,`register_time`,`modify_time`,`available`,`gender`,`province`,`city`) VALUES ('" + userId + "',NULL,NULL,NULL,'1509688041',2,'" + phone + "',NULL,'',NULL,4,'创业教练',1,'2019-08-10 14:06:31','2018-08-10 13:16:56','2018-10-17 17:20:30',1,NULL,NULL,NULL);");
                userMap.put("phone", phone);
                userMap.put("userId", userId);
            } else {
//                userMap.put("phone", phone);
//                userMap.put("userId", oneByParams.getUserId());
//                System.out.println(oneByParams.getUserId());
                if (oneByParams.getMemberLevel() < 4) {
                    System.out.println("update ss_user_id set is_buy =1 ,expires_time='2019-11-28 14:06:31',member_level=4 ,level_name='创业教练' where register_mobile='" + phone + "';");

                }
            }
//            System.out.println(userMap);
        }

//        {phone=18157976587, userId=75a1b5ff0f3b442f9b55914fbf08a692}
//        {phone=15333435338, userId=308e381d517d4d3180ce56fb76f21dae}
//        {phone=15257174450, userId=42c261ff60f648f5881de54592f0bf8f}
//        {phone=15967981237, userId=c186f651e3894074a92de525ecd5838e}
//        {phone=13755269116, userId=5abd20f965514aefaa8c4ddb1f381330}


    }

    @Test
    public void bindUser() {
        List userList = new ArrayList();
        userList.add("13588881981");
        userList.add("15088641029");
        userList.add("18635017337");
        userList.add("18625249898");
        userList.add("18267099998");
        userList.add("18957981788");
        userList.add("13777920286");
        userList.add("18923377036");
        userList.add("18005790801");
        userList.add("18660780664");
        userList.add("17731677668");
        userList.add("18611178895");
        userList.add("17550521165");
        List<String> referrerList = new ArrayList();
        referrerList.add("18157976587");
        referrerList.add("18157976587");
        referrerList.add("15333435338");
        referrerList.add("15333435338");
        referrerList.add("15257174450");
        referrerList.add("15967981237");
        referrerList.add("15257174450");
        referrerList.add("18157976587");
        referrerList.add("15967981237");
        referrerList.add("13755269116");
        referrerList.add("13755269116");
        referrerList.add("13755269116");
        referrerList.add("13755269116");

        for (int i = 0; i < userList.size(); i++) {
            String phone = (String) userList.get(i);
            User qUser = new User();
            qUser.setRegisterMobile(phone);
            User oneByParams = userService.getOneByParams(qUser);
            String userId = oneByParams.getUserId();
            String referrer = oneByParams.getReferrer();
            Integer isBuy = oneByParams.getIsBuy();
            if (referrer == null) {
                System.out.println("UPDATE ss_user set referrer =' + referrer + ' where  register_mobile ='" + phone + "';");
            }
//            else {
//                if (isBuy == 1) {
//                    System.out.println("改手机号已经死绑推荐人" + phone);
//                }
//            }
        }

        for (String refPhone : referrerList) {
            User qUser = new User();
            qUser.setRegisterMobile(refPhone);
            User oneByParams = userService.getOneByParams(qUser);
            String refUid = oneByParams.getUserId();
            System.out.println("UPDATE ss_user set referrer ='" + refUid);
        }


    }


}

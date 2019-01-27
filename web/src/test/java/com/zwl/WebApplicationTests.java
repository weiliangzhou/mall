package com.zwl;

import com.zwl.exportexcel.ExportExcelUtil;
import com.zwl.model.po.User;
import com.zwl.model.vo.ExcelVo;
import com.zwl.service.UserService;
import com.zwl.util.UUIDUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;
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
        phoneList.add("18000527799");
        phoneList.add("15882626393");
        for (String phone : phoneList) {
            Map userMap = new HashMap();
            //提供手机号查询User 不存在 则insert
            //否则 update
            User qUser = new User();
            qUser.setRegisterMobile(phone);
            User oneByParams = userService.getOneByParams(qUser);
//            System.out.println(phone+"============="+oneByParams.getUserId());
            if (oneByParams == null) {
                String userId = UUIDUtil.getUUID32();
                System.out.println("INSERT INTO `ss_user` (`user_id`,`wechat_openid`,`wechat_union_id`,`gzh_openid`,`merchant_id`,`register_from`,`register_mobile`,`real_name`,`logo_url`,`referrer`,`member_level`,`level_name`,`is_buy`,`expires_time`,`register_time`,`modify_time`,`available`,`gender`,`province`,`city`) VALUES ('" + userId + "',NULL,NULL,NULL,'1509688041',2,'" + phone + "',NULL,'',NULL,4,'创业教练',1,now(),now(),now(),1,NULL,NULL,NULL);");
                userMap.put("phone", phone);
                userMap.put("userId", userId);
            } else {
//                userMap.put("phone", phone);
//                userMap.put("userId", oneByParams.getUserId());
//                System.out.println(oneByParams.getUserId());
                if (oneByParams.getMemberLevel() < 6) {
                    System.out.println("update ss_user set is_buy =1 ,expires_time='2229-11-28 14:06:31',member_level=6 ,level_name='院长' where register_mobile='" + phone + "';");

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


    @Test
    public void testExcel() throws Exception {
        //2018-12-27 16:08:31 最后更新时间
// 定义一个新的工作簿
        Workbook wb = new HSSFWorkbook();
        Sheet sheet1 = wb.createSheet("1");
        // 使用 Sheet 接口创建一行
        List<ExcelVo> userList = new ArrayList<ExcelVo>();
//        获取教练以上的用户list
        userList = userService.getAllList();
        FileOutputStream fos = new FileOutputStream("d:\\导出用户.xls");
        // 使用工作簿提供的 write 方法向文件输出流输出
        ExportExcelUtil exportExcelUtil = new ExportExcelUtil();
        try {
            exportExcelUtil.exportExcel("导出excel", userList, fos, "yyyymmdd");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        wb.write(fos);
        fos.close();
    }

}

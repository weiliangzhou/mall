package com.zwl.serviceimpl;

import com.zwl.model.msgsend.MsgSenderConstants;
import com.zwl.service.MsgSenderService;
import com.zwl.util.HttpsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author 二师兄超级帅
 * @Title: MsgSenderServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1413:58
 */
@Service
@Slf4j
public class MsgSenderServiceImpl implements MsgSenderService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public void sendCode(String phone) {
        try {
            StringBuffer sb = new StringBuffer(MsgSenderConstants.URL);
            sb.append("un=" + MsgSenderConstants.UN);
            sb.append("&pw=" + MsgSenderConstants.PW);
            Random random = new Random();
            int msgCode = random.nextInt(999999);
            String msg = MsgSenderConstants.TEMPLATE + msgCode;
            sb.append("&phone=" + phone);
            sb.append("&msg=" + URLEncoder.encode(msg + "", "UTF-8"));
            sb.append("&rd=0");
            log.info("开始发送短信" + sb.toString());
            String result = HttpsUtils.sendGet(sb.toString(), null);
            log.info("结束发送短信" + result);
            String[] ss = result.split(",");
            if ("0".equals(ss[1].substring(0, 1))) {
                log.info("发送成功");
                //存入redis
                // 存储到redis并设置过期时间
                stringRedisTemplate.boundValueOps(phone).set(msgCode + "", 5, TimeUnit.MINUTES);

            } else
                log.error("短信发送失败" + sb.toString() + "错误原因" + ss[1].substring(0, 3));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean checkCode(String phone, String code) {
        String redisCode = stringRedisTemplate.boundValueOps(phone).get();

        if (StringUtils.isEmpty(redisCode))
            return false;
        else if (redisCode.equals(code)) {
//            删除redis
            stringRedisTemplate.delete(phone);
            return true;
        }

        return false;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
//        StringBuffer sb = new StringBuffer(MsgSenderConstants.URL);
//        sb.append("un=" + MsgSenderConstants.UN);
//        sb.append("&pw=" + MsgSenderConstants.PW);
//        Random random = new Random();
//        int msgCode = random.nextInt(999999);
//        String msg = MsgSenderConstants.TEMPLATE + msgCode;
//        sb.append("&phone=" + "17682333183");
//        sb.append("&msg=" + URLEncoder.encode(msg + "", "UTF-8"));
//        sb.append("&rd=0");
//        log.info("开始发送短信" + sb.toString());
//        String result = HttpsUtils.sendGet(sb.toString(), null);
//        log.info("结束发送短信" + result);
//        String[] ss = result.split(",");
//        if ("0".equals(ss[1].substring(0, 1)))
//            log.info("发送成功");
//        else
//            log.error("短信发送失败" + sb.toString() + "错误原因" + ss[1]);

        Random random = new Random();
        int msgCode = random.nextInt(999999);
        String msg = MsgSenderConstants.TEMPLATE + msgCode;
        System.out.println(URLEncoder.encode(msg + "", "UTF-8"));

    }


}

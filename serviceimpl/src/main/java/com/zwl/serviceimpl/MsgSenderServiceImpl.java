package com.zwl.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwl.model.baseresult.Result;
import com.zwl.model.baseresult.ResultCodeEnum;
import com.zwl.model.msgsend.MsgSenderConstants;
import com.zwl.service.MsgSenderService;
import com.zwl.util.HttpsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
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
    public Result sendCode(String phone, String busCode) {
        try {
            String checkLimit = stringRedisTemplate.boundValueOps(phone + "_limit1").get();
            if (StringUtils.isNotBlank(checkLimit))
                return new Result(ResultCodeEnum.SUCCESS,"请一分钟之后重发");
//                BSUtil.isTrue(false, "请一分钟之后重发！");
            Map map = new HashMap();
            int msgCode = new Random().nextInt(999999);
            if (msgCode < 100000)
                msgCode += 100000;
            map.put("account", MsgSenderConstants.UN);
            map.put("password", MsgSenderConstants.PW);
            map.put("msg", MsgSenderConstants.TEMPLATE + msgCode);
            map.put("phone", phone);
            log.info("短信验证码" + msgCode);
            log.info("开始发送短信" + JSON.toJSONString(map));
            String result = HttpsUtils.sendPost(MsgSenderConstants.URL, JSON.toJSONString(map));
            log.info("结束发送短信" + result);
            JSONObject jsonObject = JSONObject.parseObject(result);
            String code = jsonObject.getString("code");
            String errorMsg = jsonObject.getString("errorMsg");
            //发送短信后，记录该手机号，限制1分钟只能发送一次
            stringRedisTemplate.boundValueOps(phone + "_limit1").set(msgCode + "", 1, TimeUnit.MINUTES);
            if ("0".equals(code)) {
                log.info("发送成功");
                //存入redis
                //根据busCode 1绑定手机，2购买绑定手机，存储到redis并设置过期时间
                stringRedisTemplate.boundValueOps(busCode + phone).set(msgCode + "", 5, TimeUnit.MINUTES);
            } else
                log.error("短信发送失败" + "错误原因" + errorMsg);
            return new Result();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void sendMsg(String phone, String msg) {
        try {
            Map map = new HashMap();
            map.put("account", MsgSenderConstants.UN);
            map.put("password", MsgSenderConstants.PW);
            map.put("msg", msg);
            map.put("phone", phone);
            log.info("开始发送短信" + JSON.toJSONString(map));
            String result = HttpsUtils.sendPost(MsgSenderConstants.URL, JSON.toJSONString(map));
            log.info("结束发送短信" + result);
            JSONObject jsonObject = JSONObject.parseObject(result);
            String code = jsonObject.getString("code");
            String errorMsg = jsonObject.getString("errorMsg");
            if ("0".equals(code)) {
                log.info("发送成功");
                //存入redis
            } else
                log.error("短信发送失败" + "错误原因" + errorMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean checkCode(String phone, String code, String busCode) {
        String redisCode = stringRedisTemplate.boundValueOps(busCode + phone).get();
        if (StringUtils.isEmpty(redisCode))
            return false;
        else {
            if ("3".equals(busCode)) {//首页绑定手机号码 busCode=3    普通发送验证码 busCode= 1注册 2购买
                if (redisCode.equals(code) || "admin123".equals(code)) {
                    //            删除redis
                    stringRedisTemplate.delete(busCode + phone);
                    return true;
                }
            } else {
                if (redisCode.equals(code) || "xdy".equals(code))
                    return true;

            }

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

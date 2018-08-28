package com.zwl.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zwl.model.po.Merchant;
import com.zwl.model.vo.GzhMsgTemplate;
import com.zwl.model.wxpay.WxConstans;
import com.zwl.service.GZHService;
import com.zwl.service.MerchantService;
import com.zwl.service.MqSenderService;
import com.zwl.service.WxAccessTokenService;
import com.zwl.util.HttpsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 二师兄超级帅
 * @Title: WxSenderServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1117:09
 */
@Service
@Slf4j
public class GzhServiceImpl implements GZHService {
    @Autowired
    private WxAccessTokenService wxAccessTokenService;

    @Autowired
    private MqSenderService mqSenderService;
    @Autowired
    private MerchantService merchantService;


    @Override
    public void sendGzhMsgByOne(String openId, String className, String realNameOrPhone, String merchantId, String gzAppId, String gzAppKey, String xcxAppId, String templateId) {
//        openid="oOkIC0Yx_LCl5KgqENsBChuHH200";
//        merchantId="1509688041";
//        touser	是	接收者openid
//        template_id	是	模板ID
//        url	否	模板跳转链接
//        miniprogram	否	跳小程序所需数据，不需跳小程序可不用传该数据
//        appid	是	所需跳转到的小程序appid（该小程序appid必须与发模板消息的公众号是绑定关联关系，暂不支持小游戏）
//        pagepath	否	所需跳转到小程序的具体页面路径，支持带参数,（示例index?foo=bar），暂不支持小游戏
//        data	是	模板数据
//        color	否	模板内容字体颜色，不填默认为黑色
        String accessToken = wxAccessTokenService.getAccessToken(merchantId, gzAppId, gzAppKey, 1);
        String msgurl = WxConstans.SEND_KC_MSG + accessToken;
        SimpleDateFormat sdf_yMdHms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map miniprogramMap = new HashMap<>();
        miniprogramMap.put("appid", xcxAppId);
        miniprogramMap.put("pagepath", WxConstans.PAGEPATH);
        String miniprogram = JSON.toJSONString(miniprogramMap);
//        东遥课堂更新新课程啦！
//        课程：总裁班
//        参加人：王小二或者手机号码
//        欢迎登陆东遥课堂微信小程序收听！
        GzhMsgTemplate gzhMsgTemplate=new GzhMsgTemplate();
        Map first=new HashMap();
        first.put("value","微商夜大有新课程啦！");
        first.put("color","#173177");
        Map keyword1=new HashMap();
        keyword1.put("value",className);
        keyword1.put("color","#173177");
        Map keyword2=new HashMap();
        keyword2.put("value","微商夜大");
        keyword2.put("color","#173177");
        Map remark=new HashMap();
        remark.put("value","欢迎登陆东遥课堂微信小程序收听！");
        remark.put("color","#173177");
        gzhMsgTemplate.setFirst(first);
        gzhMsgTemplate.setKeyword1(keyword1);
        gzhMsgTemplate.setKeyword2(keyword2);
        gzhMsgTemplate.setRemark(remark);
        Map requestMap = new HashMap();
        requestMap.put("touser", openId);
        requestMap.put("appid", gzAppId);
        requestMap.put("template_id", templateId);
        requestMap.put("miniprogram", miniprogramMap);
        requestMap.put("data", gzhMsgTemplate);
        log.info("发送微信模板" + JSON.toJSONString(requestMap));
        Map msgMap = new HashMap();
        msgMap.put("msgurl", msgurl);
        msgMap.put("data", requestMap);
        Destination destination = new ActiveMQQueue("kctz.queue");
        //存入mq
        mqSenderService.sendMq(destination, JSON.toJSONString(msgMap));

    }

    @Override
    public List<String> getGzhOpenIdList(String merchantId, String gzAppId, String gzAppKey) {
        String gzhAccessToken = wxAccessTokenService.getAccessToken(merchantId, gzAppId, gzAppKey, 1);
//        String gzhOpenId = stringRedisTemplate.boundValueOps("gzhOpenId_"+code).get();
//        if (StringUtils.isBlank(gzhOpenId)) {
        String result = HttpsUtils.sendGet(WxConstans.GET_OPENID_LIST + gzhAccessToken, null);
//        {
//            "total": 251,
//                "count": 251,
//                "data": {
//            "openid": [
//            "obBoO0wNHoA6pxWzLRFrMVDzJBTE",
//                    "obBoO09WfNuQMu76QLNeQ7Ur-tLw"
//        ]
//        },
//            "next_openid": "obBoO09WfNuQMu76QLNeQ7Ur-tLw"
//        }
//        log.info("获取公众号OpenId"+result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        Integer total = jsonObject.getInteger("total");
        Integer count = jsonObject.getInteger("count");
        String next_openid = jsonObject.getString("next_openid");
        List<String> openIdList = parseOpenidList(result);
//        如果总记录数=当前记录数,则不递归
        while (total > count && count == 10000) {
            String next_result = HttpsUtils.sendGet(WxConstans.GET_OPENID_LIST + gzhAccessToken + "&next_openid=" + next_openid, null);
            JSONObject next_result_json = JSONObject.parseObject(next_result);
            total = next_result_json.getInteger("total");
            count = next_result_json.getInteger("count");
            next_openid = next_result_json.getString("next_openid");
            List<String> nextOpenIdList = parseOpenidList(next_result);
            openIdList.addAll(nextOpenIdList);
        }


//            stringRedisTemplate.boundValueOps("gzhOpenId_"+code).set(gzhOpenId, 5, TimeUnit.MINUTES);
//        }
        return openIdList;
    }

    @Override
    public void sendGzhMsgByAll(String className, String realNameOrPhone, String merchantId) {
        Merchant merchant = merchantService.getMerchantByMerchantId(merchantId);
        String gzAppId = merchant.getGzAppId();
        String gzAppKey = merchant.getGzAppKey();
        String xcxAppId = merchant.getAppId();
        List<String> openidList = getGzhOpenIdList(merchantId, gzAppId, gzAppKey);
//        List<String> openidList = new ArrayList<>();
//        openidList.add("obBoO0yVr8DwoZK47eSidlIFUE7A");
//        openidList.add("obBoO01VBFIZd1S51jEKLnkuzPfQ");
        String templateId = "niRn7EQ3Hb7pXD13o2D9JL6YpSWVqX2uV1I30EWmI8s";
        for (String openId : openidList) {
            sendGzhMsgByOne(openId, className, realNameOrPhone, merchantId, gzAppId, gzAppKey, xcxAppId, templateId);
        }
    }

    public static List<String> parseOpenidList(String result) {
        JSONObject jsonObject = JSONObject.parseObject(result);
        String data = jsonObject.getString("data");
        JSONObject data1 = JSONObject.parseObject(data);
        String openid = data1.getString("openid");
        JSONArray jsonArray = JSONArray.parseArray(openid);
        List<String> openIdList = jsonArray.toJavaList(String.class);
        return openIdList;
    }


    public static void main(String[] args) {
        String result = "{\"total\":10001,\"count\":10000,\"data\":{\"openid\":[\"obBoO0wNHoA6pxWzLRFrMVDzJBTE\",\"obBoO09WfNuQMu76QLNeQ7Ur-tLw\"]},\"next_openid\":\"obBoO09WfNuQMu76QLNeQ7Ur-tLw\"}";
        JSONObject jsonObject = JSONObject.parseObject(result);
        Integer total = jsonObject.getInteger("total");
        Integer count = jsonObject.getInteger("count");
        String next_openid = jsonObject.getString("next_openid");
        List<String> openIdList = parseOpenidList(result);
//        如果总记录数=当前记录数,则不递归
        while (total > count && count == 10000) {
            String next_result = "{\"total\":10001,\"count\":1,\"data\":{\"openid\":[\"obBoO0wNHoA6pxWzLRFrMVDzJBTE\",\"obBoO09WfNuQMu76QLNeQ7Ur-tLw\"]},\"next_openid\":\"obBoO09WfNuQMu76QLNeQ7Ur-tLw\"}";
            JSONObject next_result_json = JSONObject.parseObject(next_result);
            total = next_result_json.getInteger("total");
            count = next_result_json.getInteger("count");
            next_openid = next_result_json.getString("next_openid");
            List<String> nextOpenIdList = parseOpenidList(next_result);
            openIdList.addAll(nextOpenIdList);
        }
        for (String s : openIdList) {
            System.out.println(s);
        }

    }

//    @Override
//    public void sendXcxMsg(String formId, String productName, Integer price, String paymentNo, String merchantId, String appid, String appKey, String buyTemplateId, Integer type) {
////        openid="oOkIC0Yx_LCl5KgqENsBChuHH200";
////        merchantId="1509688041";
////        touser	是	接收者openid
////        template_id	是	模板ID
////        url	否	模板跳转链接
////        miniprogram	否	跳小程序所需数据，不需跳小程序可不用传该数据
////        appid	是	所需跳转到的小程序appid（该小程序appid必须与发模板消息的公众号是绑定关联关系，暂不支持小游戏）
////        pagepath	否	所需跳转到小程序的具体页面路径，支持带参数,（示例index?foo=bar），暂不支持小游戏
////        data	是	模板数据
////        color	否	模板内容字体颜色，不填默认为黑色
//        String accessToken = wxAccessTokenService.getAccessToken(merchantId, appid, appKey, 2);
//        String bugmsgurl = WxConstans.SEND_XCX_MSG + accessToken;
//        SimpleDateFormat sdf_yMdHms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Map miniprogramMap = new HashMap<>();
////        miniprogramMap.put("appid", appid);
//
////        miniprogramMap.put("pagepath", WxConstans.PAGEPATH);
////        String miniprogram = JSON.toJSONString(miniprogramMap);
////        购买时间
////        {{keyword1.DATA}}
////        物品名称
////        {{keyword2.DATA}}
////        交易单号
////        {{keyword3.DATA}}
////        交易金额
////        {{keyword4.DATA}}
//        //根据type 0支付1课程
//        String data = "";
//        if (type == 0) {
//            data = "\"keyword1\":{\"value\":" + sdf_yMdHms.format(new Date()) + ",\"color\":\"#173177\"}," +
//                    "\"keyword2\":{\"value\":" + productName + ",\"color\":\"#173177\"}," +
//                    "\"keyword3\":{\"value\":" + paymentNo + ",\"color\":\"#173177\"}," +
//                    "\"keyword4\":{\"value\":" + price / 100 + ",\"color\":\"#173177\"}";
//        } else {
//
//
//
//        }
//
//
//        Map requestMap = new HashMap();
//        requestMap.put("touser", formId);
//        requestMap.put("template_id", "IP_ATVNzzNIjAv8s2MMIe5ylz0sKA6ADdyVSLcd_LXI");
//        //点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
//        requestMap.put("page", buyTemplateId);
//        //表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id
//        requestMap.put("form_id", formId);
//        requestMap.put("data", data);
//        log.info("发送微信模板" + JSON.toJSONString(requestMap));
//        String result = HttpsUtils.sendPost(bugmsgurl, JSON.toJSONString(requestMap));
//        log.info("发送微信模板结果" + result);
////        {
////            "errcode":0,
////                "errmsg":"ok",
////                "msgid":200228332
////        }
//
//
//    }
}

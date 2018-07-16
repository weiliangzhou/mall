package com.zwl.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.zwl.model.wxpay.WxConstans;
import com.zwl.service.WxAccessTokenService;
import com.zwl.service.WxSenderService;
import com.zwl.util.HttpsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
public class WxSenderServiceImpl implements WxSenderService {
    @Autowired
    private WxAccessTokenService wxAccessTokenService;

    @Override
    public void sendBuyMsg(String openid, String productName, Integer price) {
//        touser	是	接收者openid
//        template_id	是	模板ID
//        url	否	模板跳转链接
//        miniprogram	否	跳小程序所需数据，不需跳小程序可不用传该数据
//        appid	是	所需跳转到的小程序appid（该小程序appid必须与发模板消息的公众号是绑定关联关系，暂不支持小游戏）
//        pagepath	否	所需跳转到小程序的具体页面路径，支持带参数,（示例index?foo=bar），暂不支持小游戏
//        data	是	模板数据
//        color	否	模板内容字体颜色，不填默认为黑色
        String accessToken = wxAccessTokenService.getAccessToken();
        String bugmsgurl = WxConstans.SEND_BUG_MSG + accessToken;
        SimpleDateFormat sdf_yMdHms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map miniprogramMap = new HashMap<>();
        miniprogramMap.put("appid", WxConstans.APPID);
        miniprogramMap.put("pagepath", WxConstans.PAGEPATH);
        String miniprogram = JSON.toJSONString(miniprogramMap);
        String data = "\"first\":{\"value\":\"恭喜你购买成功！\",\"color\":\"#173177\"},\"keyword1\":{\"value\":" + productName + ",\"color\":\"#173177\"},\"keyword2\":{\"value\":" + price/100 + ",\"color\":\"#173177\"},\"keyword3\":{\"value\":" + sdf_yMdHms.format(new Date()) + ",\"color\":\"#173177\"},\"remark\":{\"value\":\"欢迎再次购买！\",\"color\":\"#173177\"}";
        Map requestMap = new HashMap();
        requestMap.put("touser", openid);
        requestMap.put("template_id", WxConstans.BUG_TEMPLATE_ID);
        requestMap.put("miniprogram", miniprogramMap);
        requestMap.put("data", data);
        log.info("发送微信模板"+JSON.toJSONString(requestMap));
        String result = HttpsUtils.sendPost(bugmsgurl, JSON.toJSONString(requestMap));
        log.info("发送微信模板结果"+result);
//        {
//            "errcode":0,
//                "errmsg":"ok",
//                "msgid":200228332
//        }


    }
}

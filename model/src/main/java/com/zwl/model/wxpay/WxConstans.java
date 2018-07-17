package com.zwl.model.wxpay;

/**
 * @author 二师兄超级帅
 * @Title: WxConstans
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/915:18
 */
public class WxConstans {

    public static final String APPID = "wx37a5a7281317047a";
    public static final String MCHID = "1490776702";
    public static final String PAGEPATH = "pages/home/home";

    //    H5支付url
    public static final String PAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    public static final String USER_NOTIFY_URL = "https:/wx/pay_notify.do";
    //    企业付款url
    public static final String ADMIN_PAY_URL = " https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
    //    密钥设置
    public static final String PARTNERKEY = "123456789101112131415139399300Sz";
    //获取公众号全局授权，有效期为2小时，存入redis，如果redis未找到则重新发起请求
    public static final String ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx32966989d0bc098d&secret=b6ec9acfe8834c5f132ec94872f91996";
    //发送消息模版
    public static final String SEND_BUG_MSG = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
    //模版ID
    public static final String BUG_TEMPLATE_ID = "0o8fvt0Z8IhZDLJnhkaMlvH6yixgl62TXGXkJpqIhaI";


}

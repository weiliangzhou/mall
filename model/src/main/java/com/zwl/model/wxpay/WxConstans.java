package com.zwl.model.wxpay;

/**
 * @author 二师兄超级帅
 * @Title:
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/915:18
 */
public class WxConstans {
    //公众号APPID
//    public static final String APPID = "wx32966989d0bc098d";
    //小程序APPID
//    public static final String XCX_APPID = "wx754b4a23873787bb";
//    public static final String MCHID = "1509688041";
    public static final String PAGEPATH = "pages/home/home";

    //    H5支付url
    public static final String PAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //    public static final String USER_NOTIFY_URL = "http://xdy.wegoo.com.cn:18080/wx/pay/pay_notify.do";
    public static final String USER_NOTIFY_URL = "https://xcx.wegoo.cn:18080/wx/pay/pay_notify.do";
    //    企业付款url
    public static final String ADMIN_PAY_URL = " https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
    //    微信支付密钥设置
//    public static final String PARTNERKEY = "612aa0e07599468997974318e654509f";
    //获取公众号全局授权，有效期为2小时，存入redis，如果redis未找到则重新发起请求
    public static final String ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
    //发送消息模版
    public static final String SEND_BUG_MSG = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
    //模版ID
//    public static final String BUG_TEMPLATE_ID = "0o8fvt0Z8IhZDLJnhkaMlvH6yixgl62TXGXkJpqIhaI";
    //    微信授权
    public static final String WX_AUTHORIZATION = "https://api.weixin.qq.com/sns/jscode2session";
    //    查询订单
    public static final String QUERY_WX_ORDER = "https://api.mch.weixin.qq.com/pay/orderquery";


}

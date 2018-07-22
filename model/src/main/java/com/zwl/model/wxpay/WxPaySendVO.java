package com.zwl.model.wxpay;

import lombok.Data;

/**
 * @author 二师兄超级帅
 * @Title: WxPaySendVO
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/2211:32
 */
@Data
public class WxPaySendVO {
    private String realIp;
    private String openId;
    private String orderNo;
    private String totalFee;
    private String appid;
    private String mch_id;
    private String wxPayKey;
}

package com.zwl.model.wxpay;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author 二师兄超级帅
 * @Title: WxPayVo
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1810:49
 */
@Data
public class WxPayVo {
    private String timeStamp;
    private String nonceStr;
    private String packageStr;
    private String paySign;
    private String signType;
}

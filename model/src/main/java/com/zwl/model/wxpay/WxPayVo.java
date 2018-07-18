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
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private String timeStamp;
    private String nonceStr;
    private String packageStr;
    private String paySign;


}

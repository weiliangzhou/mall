package com.zwl.wxpay;

import lombok.Data;

/**
 * @author 二师兄超级帅
 * @Title: AdminPayVo
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1016:34
 */
@Data
public class AdminPayVo {
    private String realIp;
    private String openId;
    private String realName;
    private Integer amount;
    private String desc;
    private String withdrawNo;
}

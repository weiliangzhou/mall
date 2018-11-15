package com.zwl.model.vo;

import lombok.Data;

@Data
public class WxUserInfoVo2 {
    /**
     * 用户的唯一标识
     */
    private String openid;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    private Integer sex;
    /**
     * 用户特权信息，json 数组
     */
    private String province;
    /**
     * 用户头像
     */
    private String headimgurl;
    /**
     * 城市
     */
    private String city;
    /**
     * 国家
     */
    private String country;
    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
     */
    private String unionid;

    /**
     * 是否已关注过公众号
     */
    private Integer subscribe;
}

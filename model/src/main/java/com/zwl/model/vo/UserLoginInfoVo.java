package com.zwl.model.vo;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.Data;


/**
 * 用户vo
 */
@Data
public class UserLoginInfoVo {
    @ApiComment(value = "用户id", sample = "SASSUXUDONGYAO201807060001")
    private String userId;
    @ApiComment(value = "商户id", sample = "SASSMXUDONGYAO201807090001")
    private String merchantId;
    @ApiComment(value = "微信用户code", sample = "1234")
    private String code;
    @ApiComment(value = "微信用户注册的手机号", sample = "13612895656")
    private String wechatMobile;
    @ApiComment(value = "token", sample = "fjade13442f234")
    private String token;
    @ApiComment(value = "微信用户昵称", sample = "我是一只小小鸟")
    private String nickName;
    @ApiComment(value = "微信用户头像", sample = "https://wx.qlogo.cn/mmopen/vi_32/pM9miba3MPibic2cxVdbSZlEneEOKPXTqqwVwGjOwDGLXkwj049fbgPLG4HfPMedjsiaekpITiagEw5P19jIVY0ZGxw/132")
    private String logoUrl;
}

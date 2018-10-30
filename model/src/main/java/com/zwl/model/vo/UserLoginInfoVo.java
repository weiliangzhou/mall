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
    @ApiComment(value = "推荐人userID", sample = "admin")
    private String referrer;
    @ApiComment(value = "会员等级", sample = "1")
    private Integer memberLevel;
    @ApiComment(value = "公众号对应的openid", sample = "1kkjhe321")
    private String formId;
    @ApiComment(value = "是否绑定手机号", sample = "1")
    private Integer isBindMobile;
    @ApiComment(value = "用户绑定的手机号", sample = "13618452996")
    private String registerMobile;
    @ApiComment(value = "是否实名认证过", sample = "1")
    private Integer isCertification;
    @ApiComment(value = "用户实名认证状态", sample = "1")
    private Integer certificationStatus;
    @ApiComment(value = "等级名称", sample = "院长")
    private String levelName;
    @ApiComment(value = "账户余额（元）", sample = "1078000")
    private Integer balance;
    @ApiComment(value = "邀请记录人数（人）", sample = "108")
    private Integer xiaxianCount;
    @ApiComment(value = "授权业务分类", sample = "1小程序 2 H5网页授权")
    private Integer busCode;

    //------------------------------  下面 H5登录时候用到的参数
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 短信验证码
     */
    private String msgCode;
    /**
     * 微信授权code
     */
    private String wxAccreditCode;
//    账户管理
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 城市
     */
    private String city;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 身份证
     */
    private String idCardNum;

}

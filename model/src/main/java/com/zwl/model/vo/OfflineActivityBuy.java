package com.zwl.model.vo;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.groups.Buy;
import com.zwl.model.groups.SlBuy;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 二师兄超级帅
 * @Title: OfflineActivityBuy
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/9/2811:50
 */
@Data
public class OfflineActivityBuy {
    /**
     * activityId
     */
    @NotNull(message = "活动id不能为空", groups = {Buy.class, SlBuy.class})
    @ApiComment(value = "活动id", sample = "001")
    private Integer activityId;
    /**
     * 姓名
     */
    @NotBlank(message = "真实姓名", groups = {Buy.class, SlBuy.class})
    @ApiComment(value = "realName", sample = "孙悟空")
    private String realName;
    /**
     * 性别
     */
    @NotNull(message = "性别不能为空", groups = {Buy.class, SlBuy.class})
    @ApiComment(value = "sex", sample = "0 男 1女")
    private Integer sex;
    /**
     * 所在省
     */
    @NotBlank(message = "所在省", groups = {Buy.class, SlBuy.class})
    @ApiComment(value = "province", sample = "所在省")
    private String province;
    /**
     * 所在城市
     */
    @NotBlank(message = "所在城市", groups = {Buy.class, SlBuy.class})
    @ApiComment(value = "city", sample = "所在城市")
    private String city;
    /**
     * 手机
     */
    @NotBlank(message = "手机号不能为空", groups = {Buy.class, SlBuy.class})
    @ApiComment(value = "phone", sample = "13000000000")
    private String phone;
    /**
     * 身份证
     */
    @NotBlank(message = "idCardNum", groups = {Buy.class, SlBuy.class})
    @ApiComment(value = "idCardNum", sample = "33038211111111")
    private String idCardNum;

    /**
     * 线下沙龙推荐人
     */
    @NotBlank(message = "slReferrer", groups = {SlBuy.class})
    @ApiComment(value = "slReferrer", sample = "线下沙龙推荐人UUID")
    private String slReferrer;

    /**
     * 订单类似
     */
    @NotBlank(message = "orderType", groups = {SlBuy.class})
    @ApiComment(value = "orderType", sample = "0:线下活动 1:线下沙龙")
    private Integer orderType;

    /**
     * 微信号
     */
    @NotBlank(message = "wechatNo", groups = {SlBuy.class})
    @ApiComment(value = "wechatNo", sample = "微信号")
    private String wechatNo;


}


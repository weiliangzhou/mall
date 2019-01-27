package com.zwl.model.vo;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.groups.QueryInfo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 二师兄超级帅
 * @Title: UserQueryVo
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/2117:05
 */
@Data
public class UserQueryVo {
    @NotBlank(message = "商户号", groups = {QueryInfo.class})
    @ApiComment(value = "merchantId", sample = "1")
    private String merchantId;
    @NotNull(message = "pageNum", groups = {QueryInfo.class})
    @ApiComment(value = "pageNum", sample = "1")
    private Integer pageNum;
    @NotNull(message = "pageSize", groups = {QueryInfo.class})
    @ApiComment(value = "pageSize", sample = "1")
    private Integer pageSize;
    //0普通会员 1付费会员
    @ApiComment(value = "0普通会员 1付费会员", sample = "1")
    private String queryType;
    @ApiComment(value = "6院长 5班长 4学员 1小班 0普通会员", sample = "1")
    private String memberLevel;
    @ApiComment(value = "手机号", sample = "1")
    private String phone;
    //    从什么渠道注册。1、wechat 微信注册 2、线下导入
    @ApiComment(value = "渠道注册 1 微信注册 2线下导入", sample = "1")
    private Integer registerFrom;
}

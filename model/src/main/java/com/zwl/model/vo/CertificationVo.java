package com.zwl.model.vo;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.Data;

import java.util.Date;

@Data
public class CertificationVo {
    @ApiComment(value = "审核id", sample = "12")
    private Long id;
    @ApiComment(value = "审核状态", sample = "0未提交1审核中 2审核通过 3未通过")
    private Integer status;
    @ApiComment(value = "用户手机号", sample = "13825146633")
    private String registerMobile;
    @ApiComment(value = "真实姓名", sample = "张三")
    private String realname;
    @ApiComment(value = "身份证", sample = "311588199410122531")
    private String idCard;
    @ApiComment(value = "身份证正面照", sample = "https://image.baidu.com/search/detail")
    private String img1Url;
    @ApiComment(value = "身份证国徽照", sample = "https://image.baidu.com/search/detail")
    private String img2Url;
    @ApiComment(value = "手持身份证", sample = "https://image.baidu.com/search/detail")
    private String img3Url;
    @ApiComment(value = "驳回原因", sample = "身份证号有误")
    private String rejectReason;
    @ApiComment(value = "审核时间", sample = "2018-7-17 16:23:12")
    private String modifyTime;
    @ApiComment(value = "提交时间", sample = "2018-7-17 16:23:12")
    private String createTime;
}

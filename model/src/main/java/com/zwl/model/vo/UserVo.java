package com.zwl.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.Data;

import java.util.Date;

@Data
public class UserVo {
    @ApiComment(value = "userid", sample = "12")
    private String userId;
    @ApiComment(value = "会员等级", sample = "1")
    private Integer memberLevel;
    @ApiComment(value = "注册手机号", sample = "18725268879")
    private String registerMobile;
    @ApiComment(value = "会员等级名称", sample = "班长")
    private String levelName;
    @ApiComment(value = "用户真实姓名", sample = "张三")
    private String realName;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "更新时间", sample = "2018-7-20 14:12:42")
    private String modifyTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "注册时间", sample = "2018-7-20 14:12:42")
    private String registerTime;
    @ApiComment(value = "用户购买渠道1-微信，2-线下", sample = "2")
    private Integer registerFrom;
    @ApiComment(value = "推荐人真实姓名", sample = "游东方")
    private String referrerRealName;
    @ApiComment(value = "推荐人手机号", sample = "13111111111")
    private String referrerRegisterMobile;
    @ApiComment(value = "下线付费人数", sample = "8")
    private Integer xiaxianCount;

}

package com.zwl.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class XiaXianSortVo {
    @ApiComment(value = "真实姓名", sample = "二师兄")
    private String realName;
    @ApiComment(value = "手机号", sample = "13900000001")
    private String mobile;
    @ApiComment(value = "消费金额", sample = "300000")
    private Integer money;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "购买时间", sample = "2018-07-05 18:00:00")
    private Date buyTime;
    @ApiComment(value = "返佣金额", sample = "60000")
    private Integer maidMoney;
    @ApiComment(value = "等级", sample = "1")
    private Integer level;
    @ApiComment(value = "等级名称", sample = "院长")
    private String levelName;
    @ApiComment(value = "下线等级为6：院长", sample = "下线等级为院长")
    private List<XiaXianSortVo> listLevel6;
    @ApiComment(value = "下线等级为5：班长", sample = "下线等级为班长")
    private List<XiaXianSortVo> listLevel5;
    @ApiComment(value = "下线等级为4：学员", sample = "下线等级为学员")
    private List<XiaXianSortVo> listLevel4;
    @ApiComment(value = "下线等级为1：小班", sample = "下线等级为小班")
    private List<XiaXianSortVo> listLevel1;
}

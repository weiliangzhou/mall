package com.zwl.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.Data;

import java.util.Date;

/**
 * @author 二师兄超级帅
 * @Title: MaidInfoVo
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1614:33
 */
@Data
public class MaidInfoVo {
//--     用户logo
//--     realname
//--     手机号
//--     等级
//--     返佣金额
//--     创建时间
    @ApiComment(value = "logo", sample = "logo")
    private String logoUrl;
    @ApiComment(value = "真实姓名", sample = "二师兄")
    private String realName;
    @ApiComment(value = "手机号", sample = "139****0001")
    private String phone;
    @ApiComment(value = "等级", sample = "院长")
    private Integer level;
    @ApiComment(value = "等级", sample = "院长")
    private String levelName;
    @ApiComment(value = "返佣金额", sample = "9900")
    private Integer maidMoney;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "时间", sample = "2018-07-05 18:00")
    private Date createTime;

}

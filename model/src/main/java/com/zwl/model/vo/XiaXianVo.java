package com.zwl.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.Data;

import java.util.Date;

/**
 * @author 二师兄超级帅
 * @Title: XiaXianVo
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1311:30
 */
@Data
public class XiaXianVo {
    //    姓名、
//    手机号、
//    消费金额、
//    购买时间、
//    返佣收益
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


}

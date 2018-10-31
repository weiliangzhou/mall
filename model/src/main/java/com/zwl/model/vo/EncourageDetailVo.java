package com.zwl.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.Data;

import java.util.Date;

@Data
public class EncourageDetailVo {
    @ApiComment(value = "userid", sample = "asfsdfdsf5615")
    private String userId;
    @JSONField(format = "yyyy-MM")
    @ApiComment(value = "开始时间", sample = "2018-07")
    private Date startTime;
    @JSONField(format = "yyyy-MM")
    @ApiComment(value = "结束时间", sample = "2018-07")
    private Date endTime;
}

package com.zwl.model.vo;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.Data;

@Data
public class MyMaidInfoVo {
    @ApiComment("title")
    private String title;
    @ApiComment("数量")
    private Integer count;
    @ApiComment("等级")
    private Integer leval;
    @ApiComment("总数量")
    private Integer totalCount;
    @ApiComment("剩余数量")
    private Integer restCount;
}

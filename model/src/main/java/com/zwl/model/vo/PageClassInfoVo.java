package com.zwl.model.vo;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.po.ClassInfo;
import lombok.Data;

import java.util.List;

@Data
public class PageClassInfoVo {
    @ApiComment("总记录数")
    private Long totalPage;
    @ApiComment("当前页")
    private Integer pageNum;
    @ApiComment(seeClass = ClassInfo.class)
    private List<ClassInfo> list;
}

package com.zwl.model.vo;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.po.ClassCategory;
import lombok.Data;

import java.util.List;

@Data
public class PageClassCategoryVo {
    @ApiComment(value = "当前页码", sample = "1")
    private Integer pageNum;
    @ApiComment(value = "每页显示条数", sample = "2")
    private Integer pageSize;
    @ApiComment("总记录数")
    private Long totalPage;
    @ApiComment(seeClass = ClassCategory.class)
    private List<ClassCategory> list;
}

package com.zwl.model.vo;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.po.UserCertification;
import lombok.Data;

import java.util.List;
@Data
public class PageUserVo {
    @ApiComment(value = "当前页码", sample = "1")
    private Integer pageNum;
    @ApiComment(value = "每页显示条数", sample = "2")
    private Integer pageSize;
    @ApiComment("总记录数")
    private Long totalPage;
    @ApiComment(seeClass = UserCertification.class)
    private List<UserVo> list;
}

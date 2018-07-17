package com.zwl.model.vo;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.Data;

import java.util.Date;

@Data
public class PageClassCategoryVo {
    @ApiComment(value = "当前页码", sample = "1")
    private Integer pageNum;
    @ApiComment(value = "每页显示条数", sample = "2")
    private Integer pageSize;
    /*@ApiComment(value = "课程分类ID", sample = "1")
    private Long id;
    @ApiComment(value = "名称", sample = "朋友圈分享")
    private String title;
    @ApiComment(value = "是否显示", sample = "1")
    private Integer isShow;
    @ApiComment(value = "商户id", sample = "0571XUDONGYAO")
    private String merchantId;
    @ApiComment(value = "创建时间", sample = "2018-7-17 15:09:21")
    private Date createTime;
    @ApiComment(value = "修改时间", sample = "2018-7-17 15:09:38")
    private Date modifyTime;
    @ApiComment(value = "逻辑删除位", sample = "0")
    private Integer available;*/
}

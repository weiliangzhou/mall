package com.zwl.model.vo;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.po.Video;
import lombok.Data;

@Data
public class QueryTypeVideoVo extends Video {
    @ApiComment(value = "查询条件", sample = "0查询推荐视频，1查询全部视频")
    private Integer queryType;
    @ApiComment(value = "时长分", sample = "10")
    private Integer minute;
    @ApiComment(value = "时长秒", sample = "10")
    private Integer second;
}

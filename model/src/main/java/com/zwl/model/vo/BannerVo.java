package com.zwl.model.vo;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.po.Banner;
import lombok.Data;
import java.util.List;

@Data
public class BannerVo {
    @ApiComment("总记录数")
    private Long totalPage;
    @ApiComment("当前页")
    private Integer pageNum;
    @ApiComment(seeClass =Banner.class)
    private List<Banner> bannerList;
}

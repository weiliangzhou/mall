package com.zwl.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 套课和单独的节课程列表vo
 */
@Data
public class ClassVo {
    @ApiComment(value = "套或节课程id", sample = "1")
    private Long id;
    @ApiComment(value = "套或节课程标题", sample = "新人创业培训")
    private String title;
    @ApiComment(value = "所属的课程分类名称", sample = "朋友圈学习")
    private String categoryTitle;
    @ApiComment(value = "套或节课程新建时间", sample = "2018-7-19 14:05:24")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @ApiComment(value = "套或节课程更新时间", sample = "2018-7-19 14:05:24")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;
    @ApiComment(value = "商户id", sample = "0571XUDONGYAO")
    private String merchantId;
    @ApiComment(value = "1-套，2-节", sample = "1")
    private Integer classType;
    @ApiComment(value = "浏览人数", sample = "858")
    private Integer browseCount;
    @ApiComment(value = "图片", sample = "858")
//    如果是堂，logo是节的可配置优先级），
//    按照发布时间倒序
    private String logoUrl;
    //如果是套，返回下属的节课程
    private List<ClassVo> children;
    @ApiComment(value = "最低观看要求等级", sample = "1")
    private Integer requiredMemberLevel;
}

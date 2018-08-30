package com.zwl.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.terran4j.commons.restpack.RestPackIgnore;
import lombok.Data;
import org.apache.ibatis.annotations.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 节课程
 */
@Data
public class ClassInfo {
    @ApiComment(value = "节课程id", sample = "1")
    private Long id;
    @ApiComment(value = "所属套课程id", sample = "1")
    private Long classSetId;
    @NotNull(message = "所属分类不能为空", groups = {Update.class})
    @ApiComment(value = "所属课程分类id", sample = "1")
    private Long categoryId;
    @NotBlank(message = "商户号不能为空", groups = {Update.class})
    @JSONField(serialize = false)
    @RestPackIgnore
    @ApiComment(value = "商户id", sample = "0571XUDONGYAO")
    private String merchantId;
    @NotBlank(message = "音视频url不能为空", groups = {Update.class})
    @ApiComment(value = "音视频url", sample = "www.test.com")
    private String audioUrl;
    @NotBlank(message = "图片url不能为空", groups = {Update.class})
    @ApiComment(value = "图片url", sample = "www.test.com")
    private String logoUrl;
    @NotBlank(message = "节课标题不能为空", groups = {Update.class})
    @ApiComment(value = "节课程标题", sample = "布娃娃微商创业分享新手教程")
    private String title;
    @NotNull(message = "是否展示不能为空", groups = {Update.class})
    @ApiComment(value = "用户端是否显示", sample = "1")
    private Integer isShow = 1;
    @ApiComment(value = "节课程内容", sample = "一段带个格式的内容，反正很长就是了")
    private String content;
    @ApiComment(value = "节课程收听人数", sample = "563")
    private Long listenCount;
    @ApiComment(value = "创建时间", sample = "2018-7-19 14:30:41")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "修改时间", sample = "2018-7-19 14:30:50")
    private Date modifyTime;
    @ApiComment(value = "逻辑删除位", sample = "1")
    private Integer available = 1;
    @ApiComment(value = "不带格式介绍", sample = "不带格式介绍")
    private String contentText;
    @NotNull(message = "节课类型不能为空", groups = {Update.class})
    @ApiComment(value = "节课类型", sample = "0音频 1视频")
    private Integer style;
    @NotNull(message = "是否推荐不能为空", groups = {Update.class})
    @ApiComment(value = "是否推荐", sample = "0不推荐，1推荐")
    private Integer isRecommend = 0;
    @ApiComment(value = "课程时长", sample = "10")
    private Integer playTime;
}
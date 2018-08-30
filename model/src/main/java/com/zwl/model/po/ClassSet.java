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
 * 套课程
 */
@Data
public class ClassSet {
    @ApiComment(value = "套课ID", sample = "1")
    private Long id;
    @NotBlank(message = "套课标题不能为空", groups = {Update.class})
    @ApiComment(value = "套课标题", sample = "VIP课程")
    private String title;
    @NotBlank(message = "bannerUrl不能为空", groups = {Update.class})
    @ApiComment(value = "bannerUrl", sample = "www.baidu.com")
    private String bannerUrl;
    @NotNull(message = "所属分类不能为空", groups = {Update.class})
    @ApiComment(value = "所属分类id", sample = "1")
    private Integer categoryId;
    @NotBlank(message = "商户号不能为空", groups = {Update.class})
    @JSONField(serialize = false)
    @RestPackIgnore
    private String merchantId;
    @NotNull(message = "是否展示不能为空", groups = {Update.class})
    @ApiComment(value = "是否展示", sample = "0不展示，1展示")
    private Integer isShow = 1;
    @NotNull(message = "最低等级要求不能为空", groups = {Update.class})
    @ApiComment(value = "最低等级要求", sample = "4")
    private Integer requiredMemberLevel;
    @ApiComment(value = "创建时间", sample = "2018-07-05 18:00:00")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "更新时间", sample = "2018-07-05 18:00:00")
    private Date modifyTime;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer available = 1;
    @ApiComment(value = "内容", sample = "<p>10大板块，100节课让微商创业再也没有秘密！绝对是秒杀全网的课程！")
    private String content;
    @ApiComment(value = "不带格式介绍", sample = "不带格式介绍")
    private String contentText;
    @ApiComment(value = "套课类型", sample = "0音频 1视频")
    private Integer style;
    @NotNull(message = "是否推荐不能为空", groups = {Update.class})
    @ApiComment(value = "是否推荐", sample = "0不推荐 1推荐")
    private Integer isRecommend = 0;
    @NotBlank(message = "套课封面不能为空", groups = {Update.class})
    @ApiComment(value = "封面", sample = "www.baidu.com")
    private String frontCover;
}
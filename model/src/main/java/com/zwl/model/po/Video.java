package com.zwl.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.terran4j.commons.restpack.RestPackIgnore;
import lombok.Data;
import org.apache.ibatis.annotations.Update;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class Video {
    @NotNull(message = "视频ID不能为空", groups = {Update.class})
    @ApiComment(value = "视频ID", sample = "1")
    private Integer id;
    @ApiComment(value = "图片url", sample = "http://chuang-saas.oss-cn-hangzhou.aliyuncs.com/1.png")
    private String imageUrl;
    @ApiComment(value = "视频url", sample = "http://chuang-saas.oss-cn-hangzhou.aliyuncs.com/1.mp3")
    private String videoUrl;
    @ApiComment(value = "标题", sample = "世界微商大会的一天")
    private String title;
    @ApiComment(value = "内容", sample = "<p>10大板块，100节课让微商创业再也没有秘密！绝对是秒杀全网的课程！")
    private String content;
    @JSONField(serialize = false)
    @RestPackIgnore
    private String merchantId;
    @ApiComment(value = "创建时间", sample = "2018-07-05 18:00:00")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "更新时间", sample = "2018-07-05 18:00:00")
    private Date modifyTime;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer available = 1;

}

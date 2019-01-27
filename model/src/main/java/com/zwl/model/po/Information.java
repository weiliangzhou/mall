package com.zwl.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.terran4j.commons.restpack.RestPackIgnore;
import lombok.Data;
import org.apache.ibatis.annotations.Update;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class Information {
    @ApiComment(value = "产品ID", sample = "1")
    private Integer id;
    @JSONField(serialize = false)
    @RestPackIgnore
    @NotBlank(message = "merchantId不能为空", groups = {Update.class})
    private String merchantId;
    @ApiComment(value = "url", sample = "http://www.baidu.com")
    private String url;
    //是否发布状态 默认0不发布 1发布
    @ApiComment(value = "是否发布状态 ", sample = "默认0不发布 1发布")
    private Integer isShow;
    @ApiComment(value = "标题 ", sample = "小米上市了")
    @NotBlank(message = "title不能为空", groups = {Update.class})
    private String title;
    @NotBlank(message = "logoUrl不能为空", groups = {Update.class})
    @ApiComment(value = "logoUrl", sample = "logoUrl")
    private String logoUrl;
    @ApiComment(value = "音频地址", sample = "音频地址")
    private String audioUrl;
    @NotBlank(message = "内容不能为空", groups = {Update.class})
    @ApiComment(value = "内容", sample = "内容")
    private String content;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "创建时间", sample = "2018-07-05 18:00:00")
    private Date createTime = new Date();
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "更新时间", sample = "2018-07-05 18:00:00")
    private Date modifyTime;
    @JSONField(serialize = false)
    @RestPackIgnore
    private Integer available = 1;
    @ApiComment(value = "套课或者节课不带格式简介", sample = "不该格式哈哈哈哈")
    private String contentText;


}
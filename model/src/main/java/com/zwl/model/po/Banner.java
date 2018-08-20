package com.zwl.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.terran4j.commons.restpack.RestPackIgnore;
import lombok.Data;
import org.apache.ibatis.annotations.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class Banner {
    @ApiComment(value = "ID", sample = "1")
    private Integer id;
    @NotBlank(message = "banner图片路径不能为空", groups = {Update.class})
    @ApiComment(value = "banner图片路径",sample = "D:\\img")
    private String imageUrl;
    @NotBlank(message = "banner跳转路径不能为空", groups = {Update.class})
    @ApiComment(value = "banner跳转路径",sample = "http://www.google.com")
    private String hrefUrl;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "创建时间", sample = "2018-07-05 18:00:00")
    private Date createTime = new Date();
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "更新时间", sample = "2018-07-05 18:00:00")
    private Date modifyTime;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer available = 1;
    @NotBlank(message = "商户ID不能为空", groups = {Update.class})
    @JSONField(serialize = false)
    @RestPackIgnore
    private String merchantId;
    @NotNull(message = "是否展示不能为空", groups = {Update.class})
    @ApiComment(value = "是否展示", sample = "0不展示，1展示")
    private Integer isShow = 0;
}

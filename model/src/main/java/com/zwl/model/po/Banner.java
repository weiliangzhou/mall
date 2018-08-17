package com.zwl.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.terran4j.commons.restpack.RestPackIgnore;
import com.zwl.model.groups.Buy;
import com.zwl.model.groups.H5Buy;
import lombok.Data;
import org.apache.ibatis.annotations.Update;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class Banner {

    @NotNull(message = "ID", groups = {Update.class})
    @ApiComment(value = "ID", sample = "1")
    private Long id;
    @NotNull(message = "商户ID", groups = {Update.class})
    @ApiComment(value = "商户ID", sample = "1")
    private String  merchantId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "创建时间", sample = "2018-07-05 18:00:00")
    private Date createTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "更新时间", sample = "2018-07-05 18:00:00")
    private Date modifyTime;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer available = 1;
    @NotNull(message = "图片路径不能为空")
    @ApiComment(value = "图片路径",sample = "D:\\img")
    private String imageUrl;
    @NotNull(message = "点击跳转路径不能为空")
    @ApiComment(value = "图片路径",sample = "http://www.google.com")
    private String hrefUrl;



}

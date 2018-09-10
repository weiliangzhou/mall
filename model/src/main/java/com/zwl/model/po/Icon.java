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
public class Icon {
    @ApiComment(value = "图标ID", sample = "1")
    private Integer id;
    @NotBlank(message = "图标名称不能为空", groups = {Update.class})
    @ApiComment(value = "图标", sample = "关于夜大")
    private String title;
    @NotBlank(message = "图片url不能为空", groups = {Update.class})
    @ApiComment(value = "图片url", sample = "http://chuang-saas.oss-cn-hangzhou.aliyuncs.com/1.png")
    private String pictureUrl;
//    @NotBlank(message = "跳转url不能为空", groups = {Update.class})
    @ApiComment(value = "跳转url", sample = "http://www.baidu.com")
    private String hrefUrl;
    @NotNull(message = "跳转类型不能为空", groups = {Update.class})
    @ApiComment(value = "跳转类型",sample = "0不跳转 1应用内跳转 2应用外跳转")
    private Integer hrefType;
    @ApiComment(value = "端口类型",sample = "0小程序 1、H5")
    private Integer portType;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer available = 1;
    @NotBlank(message = "商户号不能为空", groups = {Update.class})
    @JSONField(serialize = false)
    @RestPackIgnore
    private String merchantId;
    @ApiComment(value = "创建时间", sample = "2018-07-05 18:00:00")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime = new Date();
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "更新时间", sample = "2018-07-05 18:00:00")
    private Date modifyTime = new Date();
}
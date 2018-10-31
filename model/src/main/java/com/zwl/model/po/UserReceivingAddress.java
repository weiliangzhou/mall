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
public class UserReceivingAddress {
    @ApiComment(value = "收货地址ID", sample = "1")
    private Integer id;
    @NotBlank(message = "收货人不能为空", groups = {Update.class})
    @ApiComment(value = "收货人", sample = "孙悟空")
    private String receivingName;
    @NotBlank(message = "省不能为空", groups = {Update.class})
    @ApiComment(value = "省", sample = "浙江省")
    private String province;
    @NotBlank(message = "市不能为空", groups = {Update.class})
    @ApiComment(value = "市", sample = "杭州市")
    private String city;
    @NotBlank(message = "区不能为空", groups = {Update.class})
    @ApiComment(value = "区", sample = "西湖区")
    private String area;
    @NotBlank(message = "地址不能为空", groups = {Update.class})
    @ApiComment(value = "地址", sample = "文二路11号")
    private String address;
    @NotNull(message = "是否默认地址不能为空", groups = {Update.class})
    @ApiComment(value = "是否默认地址", sample = "0不是1是")
    private Integer isDefault;
    @ApiComment(value = "用户ID", sample = "2752aa450f544ea1b204b3ccfbe53a92")
    private String userId;
    @JSONField(serialize = false)
    @RestPackIgnore
    @NotBlank(message = "merchantId不能为空", groups = {Update.class})
    @ApiComment(value = "商户号", sample = "1509688041")
    private String merchantId;
    @ApiComment(value = "创建时间", sample = "2018-08-09 17:51:31")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "更新时间", sample = "2018-08-09 17:51:31")
    private Date modifyTime;
    @JSONField(serialize = false)
    @RestPackIgnore
    @ApiComment(value = "逻辑删除位", sample = "1")
    private Integer available;
    @NotBlank(message = "手机号不能为空", groups = {Update.class})
    @ApiComment(value = "手机号", sample = "13800000000")
    private String phone;
}
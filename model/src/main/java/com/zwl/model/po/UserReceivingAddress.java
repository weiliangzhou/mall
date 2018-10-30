package com.zwl.model.po;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.Data;
import org.apache.ibatis.annotations.Update;

import javax.validation.constraints.NotBlank;

@Data
public class UserReceivingAddress {
    private Integer id;
    @NotBlank(message = "收货人不能为空", groups = {Update.class})
    @ApiComment(value = "收货人", sample = "张三")
    private String receivingName;
    @NotBlank(message = "省份不能为空", groups = {Update.class})
    @ApiComment(value = "省", sample = "张三")
    private String province;
    @NotBlank(message = "城市不能为空", groups = {Update.class})
    @ApiComment(value = "城市", sample = "张三")
    private String city;
    @NotBlank(message = "地区不能为空", groups = {Update.class})
    @ApiComment(value = "地区", sample = "张三")
    private String area;
    @NotBlank(message = "详细地址不能为空", groups = {Update.class})
    @ApiComment(value = "详细地址", sample = "张三")
    private String address;
    @ApiComment(value = "是否默认地址 0不是 1是", sample = "张三")
    private Integer isDefault;
    private String userId;


}
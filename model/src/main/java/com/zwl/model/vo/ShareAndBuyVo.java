package com.zwl.model.vo;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.Data;

@Data
public class ShareAndBuyVo {
    @ApiComment(value = "是否可分享", sample = "0不可分享 1可分享")
    private Integer isShare;
    @ApiComment(value = "是否可购买", sample = "0不可购买 1可购买")
    private Integer isBuy;
}

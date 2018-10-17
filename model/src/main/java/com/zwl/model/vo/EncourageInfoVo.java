package com.zwl.model.vo;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.Data;

/**
 * @author pengliang
 * @Title: EncourageInfoVo
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/10/15 15:59
 */
@Data
public class EncourageInfoVo {
    @ApiComment("累计提现金额")
    private Integer totalMoney;
    @ApiComment("可提现金额")
    private  Integer balance;
}

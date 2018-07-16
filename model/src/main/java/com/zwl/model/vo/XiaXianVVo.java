package com.zwl.model.vo;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.Data;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: XiaXianVVo
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1314:47
 */
@Data
public class XiaXianVVo {
    @ApiComment("累计返佣")
    private Integer totalMoney;
    @ApiComment("账户余额")
    private Integer balance;
    @ApiComment(seeClass = XiaXianVo.class)
    private List<XiaXianVo> xiaXianVoList;

}

package com.zwl.model.vo;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.po.Withdraw;
import lombok.Data;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: WithdrawVo
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1711:06
 */

@Data
public class WithdrawVo {
    @ApiComment("总记录数")
    private Long total;
    @ApiComment("总页数")
    private Long totalPage;
    @ApiComment("当前页")
    private Integer pageNum;
    @ApiComment(seeClass = Withdraw.class)
    private List<Withdraw> withdrawList;
}

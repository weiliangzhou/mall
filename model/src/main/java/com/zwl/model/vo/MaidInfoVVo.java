package com.zwl.model.vo;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.Data;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: MaidInfoVVo
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1615:49
 */
@Data
public class MaidInfoVVo {
    @ApiComment("累计分佣金额")
    private Integer totalAmount;
    //    剩余可邀请小班名额
    @ApiComment("剩余可邀请小班名额")
    private Integer count;
    @ApiComment(seeClass = MaidInfoVo.class)
    private List<MaidInfoVo> maidInfoVoList;
}

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
    @ApiComment("总共可邀请小班名额")
    private Integer totalCount;
    @ApiComment("推荐人的手机号码")
    private String referrerPhone;
    @ApiComment("用户头像")
    private String logoUrl;
    @ApiComment(seeClass = MaidInfoVo.class)
    private List<MaidInfoVo> maidInfoVoList;
    @ApiComment("推荐人的真实姓名")
    private String referrerRealName;
}

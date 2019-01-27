package com.zwl.service;

import com.zwl.model.po.MaidInfo;
import com.zwl.model.vo.EncourageDetailVo;
import com.zwl.model.vo.MaidInfoVo;
import com.zwl.model.vo.MyMaidInfoVVo;
import com.zwl.model.vo.XiaXianVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: MaidInfoService
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1011:58
 */
public interface MaidInfoService {
    int save(MaidInfo maidInfo);

    List<MaidInfoVo> getMaidInfoList(String userId);

    List<XiaXianVo> getXiaXianList(String userId);

    Integer getXiaXianCountByUserId(String userId);


    Integer getTotalMaidMoneyByUserId(String userId);

    Integer getMaidInfoCount(String userId);

    Integer getTotalAmountByMonthByUserId(String userId);

    List<MyMaidInfoVVo> getMaidInfoListByLevel(@Param(value="userId")String userId, @Param(value="level")Integer level);

    List<MyMaidInfoVVo> getEncourageDetail(EncourageDetailVo encourageDetailVo);

    List<MaidInfoVo> getMaidInfoByMonth(String userId);

    Integer getBalance(String userId);
}

package com.zwl.service;

import com.zwl.model.po.MaidInfo;
import com.zwl.model.vo.MaidInfoVo;
import com.zwl.model.vo.XiaXianVo;

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
}

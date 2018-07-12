package com.zwl.service;

import com.zwl.model.MaidInfo;

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

    List<MaidInfo> getMaidInfoList(String userId);
}

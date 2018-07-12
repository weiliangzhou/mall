package com.zwl.serviceimpl;

import com.zwl.dao.mapper.MaidInfoMapper;
import com.zwl.model.MaidInfo;
import com.zwl.service.MaidInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: MaidInfoServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1011:58
 */
@Service
public class MaidInfoServiceImpl implements MaidInfoService {
    @Autowired
    private MaidInfoMapper maidInfoMapper;

    @Override
    public int save(MaidInfo maidInfo) {
        return maidInfoMapper.insertSelective(maidInfo);
    }

    @Override
    public List<MaidInfo> getMaidInfoList(String userId) {
        return maidInfoMapper.getMaidInfoList(userId);
    }
}

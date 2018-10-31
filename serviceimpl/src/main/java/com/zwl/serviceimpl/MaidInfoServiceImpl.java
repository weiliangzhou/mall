package com.zwl.serviceimpl;

import com.zwl.dao.mapper.MaidInfoByMonthMapper;
import com.zwl.dao.mapper.MaidInfoMapper;
import com.zwl.model.po.MaidInfo;
import com.zwl.model.vo.EncourageDetailVo;
import com.zwl.model.vo.MaidInfoVo;
import com.zwl.model.vo.MyMaidInfoVVo;
import com.zwl.model.vo.XiaXianVo;
import com.zwl.service.MaidInfoService;
import org.apache.ibatis.annotations.Param;
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
    @Autowired
    private MaidInfoByMonthMapper maidInfoByMonthMapper;

    @Override
    public int save(MaidInfo maidInfo) {
        return maidInfoMapper.insertSelective(maidInfo);
    }

    @Override
    public List<MaidInfoVo> getMaidInfoList(String userId) {
        return maidInfoMapper.getMaidInfoList(userId);
    }

    @Override
    public List<XiaXianVo> getXiaXianList(String userId) {
        return maidInfoMapper.getXiaXianList(userId);
    }

    @Override
    public Integer getXiaXianCountByUserId(String userId) {
        return maidInfoMapper.getXiaXianCountByUserId(userId);
    }

    @Override
    public Integer getTotalMaidMoneyByUserId(String userId) {
        return maidInfoMapper.getTotalMaidMoneyByUserId(userId);
    }

    @Override
    public Integer getMaidInfoCount(String userId) {
        return maidInfoMapper.getMaidInfoCount(userId);
    }

    @Override
    public Integer getTotalAmountByMonthByUserId(String userId) {
        return maidInfoByMonthMapper.getTotalAmountByMonthByUserId(userId);
    }
    @Override
    public List<MyMaidInfoVVo> getMaidInfoListByLevel(@Param(value="userId")String userId, @Param(value="level")Integer level){
        return maidInfoMapper.getMaidInfoListByLevel(userId, level);
    }
    @Override
    public List<MyMaidInfoVVo> getEncourageDetail(EncourageDetailVo encourageDetailVo){
        return maidInfoMapper.getEncourageDetail(encourageDetailVo);
    }
    @Override
    public List<MaidInfoVo> getMaidInfoByMonth(String userId){
        return maidInfoMapper.getMaidInfoByMonth(userId);
    };
    @Override
    public Integer getBalance(String userId){
        return maidInfoMapper.getBalance(userId);
    };

}

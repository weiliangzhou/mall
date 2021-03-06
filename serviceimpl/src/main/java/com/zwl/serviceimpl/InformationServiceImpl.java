package com.zwl.serviceimpl;

import com.zwl.dao.mapper.InformationMapper;
import com.zwl.model.po.Information;
import com.zwl.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: InformationServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1215:07
 */
@Service
public class InformationServiceImpl implements InformationService {
    @Autowired
    private InformationMapper informationMapper;

    @Override
    public List<Information> getInformationList(Information information) {
        return informationMapper.getInformationList(information);
    }

    @Override
    public int addInformation(Information information) {
        return informationMapper.insertSelective(information);
    }

    @Override
    public int updateInformation(Information information) {
        return informationMapper.updateByPrimaryKeySelective(information);
    }

    @Override
    public int deleteInformation(Integer id) {
        return informationMapper.deleteInformation(id);
    }

    @Override
    public List<Information> getWxInformationList(Information information) {
        return informationMapper.getWxInformationList(information);
    }

    @Override
    public List<Information> getInformationInfo(Information information) {
        return informationMapper.getInformationInfo(information);
    }
}

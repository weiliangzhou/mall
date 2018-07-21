package com.zwl.serviceimpl;

import com.zwl.dao.mapper.ClassInfoStatisticsMapper;
import com.zwl.model.po.ClassInfoStatistics;
import com.zwl.service.ClassInfoStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@SuppressWarnings("all")
@Service
public class ClassInfoStatisticsServiceImpl implements ClassInfoStatisticsService {
    @Autowired
    private ClassInfoStatisticsMapper classInfoStatisticsMapper;

    @Override
    public int insert(ClassInfoStatistics classInfoStatistics) {
        return classInfoStatisticsMapper.insert(classInfoStatistics);
    }

    @Override
    public int setpAddBrowseCount(Long classInfoId) {
        return classInfoStatisticsMapper.setpAddBrowseCount(classInfoId);
    }

    @Override
    public ClassInfoStatistics getByClassInfoId(Long classInfoId) {
        return classInfoStatisticsMapper.selectByClassInfoId(classInfoId);
    }
}

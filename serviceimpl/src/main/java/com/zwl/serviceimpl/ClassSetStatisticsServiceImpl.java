package com.zwl.serviceimpl;

import com.zwl.dao.mapper.ClassSetStatisticsMapper;
import com.zwl.model.po.ClassSetStatistics;
import com.zwl.service.ClassSetStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@SuppressWarnings("all")
@Service
public class ClassSetStatisticsServiceImpl implements ClassSetStatisticsService {
    @Autowired
    private ClassSetStatisticsMapper classSetStatisticsMapper;

    @Override
    public int add(ClassSetStatistics classSetStatistics) {
        return classSetStatisticsMapper.insert(classSetStatistics);
    }

    @Override
    public int setpAddBrowseCount(Long classSetId) {
        return classSetStatisticsMapper.setpAddBrowseCount(classSetId);
    }

    @Override
    public ClassSetStatistics getByClassSetId(Long classSetId) {
        return classSetStatisticsMapper.selectByClassSetId(classSetId);
    }
}

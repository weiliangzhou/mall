package com.zwl.serviceimpl;

import com.zwl.dao.mapper.OperateUserRecordMapper;
import com.zwl.model.po.OperateUserRecord;
import com.zwl.service.OperateUserRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@SuppressWarnings("all")
@Service
public class OperateUserRecordServiceImpl implements OperateUserRecordService {
    @Autowired
    private OperateUserRecordMapper operateUserRecordMapper;
    @Override
    public int add(OperateUserRecord operateUserRecord) {
        return operateUserRecordMapper.insert(operateUserRecord);
    }
}

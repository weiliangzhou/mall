package com.zwl.serviceimpl;

import com.zwl.dao.mapper.OfflineActivityOperatorMapper;
import com.zwl.model.po.OfflineActivityOperator;
import com.zwl.service.OfflineActivityOperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfflineActivityOperatorServiceImpl implements OfflineActivityOperatorService {
    @Autowired
    private OfflineActivityOperatorMapper offlineActivityOperatorMapper;
    @Override
    public OfflineActivityOperator selectByOperatorAndPassword(OfflineActivityOperator offlineActivityOperator) {
        return offlineActivityOperatorMapper.selectByOperatorAndPassword(offlineActivityOperator);
    }

    @Override
    public OfflineActivityOperator getOneByOperator(String operator) {
        return offlineActivityOperatorMapper.getOneByOperator(operator);
    }
}

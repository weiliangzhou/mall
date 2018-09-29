package com.zwl.dao.mapper;

import com.zwl.model.po.OfflineActivityOperator;

public interface OfflineActivityOperatorMapper {
    OfflineActivityOperator selectByOperatorAndPassword(OfflineActivityOperator offlineActivityOperator);
    OfflineActivityOperator getOneByOperator(String operator);
}
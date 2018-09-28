package com.zwl.service;

import com.zwl.model.po.OfflineActivityOperator;

public interface OfflineActivityOperatorService {

    OfflineActivityOperator selectByOperatorAndPassword(OfflineActivityOperator offlineActivityOperator);
}

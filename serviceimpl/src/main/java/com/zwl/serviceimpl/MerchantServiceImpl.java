package com.zwl.serviceimpl;

import com.zwl.dao.mapper.MerchantMapper;
import com.zwl.model.po.Merchant;
import com.zwl.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantServiceImpl implements MerchantService {
    @Autowired
    private MerchantMapper merchantMapper;
    @Override
    public Merchant getMerchantByMerchantId(String merchantId) {
        return merchantMapper.selectByMerchantId(merchantId);
    }
}

package com.zwl.dao.mapper;

import com.zwl.model.po.Merchant;

public interface MerchantMapper {

    int insert(Merchant record);

    Merchant selectByMerchantId(String merchantId);

}
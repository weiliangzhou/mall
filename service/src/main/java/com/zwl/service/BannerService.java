package com.zwl.service;

import com.zwl.model.po.Banner;

import java.util.List;

public interface BannerService {



        int insert(Banner banner);

        List<Banner> selectBanner(Banner banner);

        int deleteBanner( Integer id);

        int updateByPrimaryKey(Banner banner);

        int insertSelective(Banner banner);
        int updateByPrimaryKeySelective(Banner banner);

}

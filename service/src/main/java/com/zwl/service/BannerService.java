package com.zwl.service;

import com.zwl.model.po.Banner;

import java.util.List;

public interface BannerService {
    List<Banner> getWxBannerList(Banner banner);

    List<Banner> getBannerList(Banner banner);

    int addBanner(Banner banner);

    int deleteBanner(Integer id);

    int updateBanner(Banner banner);

}

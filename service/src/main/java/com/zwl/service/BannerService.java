package com.zwl.service;

import com.zwl.model.po.Banner;

import java.util.List;

public interface BannerService {
      List<Banner> getBannerList();

      int insertBanner(Banner banner);

      int  deleteBanner(Long id);

      int  updateBanner(Banner banner);
}

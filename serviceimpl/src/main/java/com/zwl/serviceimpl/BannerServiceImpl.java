package com.zwl.serviceimpl;

import com.zwl.dao.mapper.BannerMapper;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.Banner;
import com.zwl.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public List<Banner> getBannerList() {
        return bannerMapper.getBannerList();
    }

    @Override
    public int insertBanner(Banner banner) {
        return bannerMapper.insertBanner(banner);
    }

    @Override
    public int deleteBanner(Long id) {
        return bannerMapper.deleteBanner(id);
    }

    @Override
    public int updateBanner(Banner banner) {11111
        return bannerMapper.updateBanner(banner);
    }


}

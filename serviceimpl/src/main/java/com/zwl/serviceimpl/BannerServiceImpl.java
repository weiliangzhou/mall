package com.zwl.serviceimpl;

import com.zwl.dao.mapper.BannerMapper;
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
    public List<Banner> getWxBannerList(Banner banner) {
        return bannerMapper.getWxBannerList(banner);
    }

    @Override
    public List<Banner> getBannerList(Banner banner) {
        return bannerMapper.getBannerList(banner);
    }

    @Override
    public int addBanner(Banner banner) {
        return bannerMapper.addBanner(banner);
    }

    @Override
    public int deleteBanner(Integer id) {
        return bannerMapper.deleteBanner(id);
    }

    @Override
    public int updateBanner(Banner banner) {
        return bannerMapper.updateBanner(banner);
    }

}

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
    public int insert(Banner banner) {
        return bannerMapper.insert(banner);
    }

    @Override
    public int insertSelective(Banner banner) {
        return bannerMapper.insertSelective(banner);
    }

    @Override
    public List<Banner> selectBanner(Banner banner) {
        return bannerMapper.selectBanner(banner);
    }

    @Override
    public int deleteBanner(Integer id) {
        return bannerMapper.deleteBanner(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Banner banner) {
        return bannerMapper.updateByPrimaryKeySelective(banner);
    }

    @Override
    public int updateByPrimaryKey(Banner banner) {
        return bannerMapper.updateByPrimaryKey(banner);
    }
}

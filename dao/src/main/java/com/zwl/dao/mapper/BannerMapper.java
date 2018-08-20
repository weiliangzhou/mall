package com.zwl.dao.mapper;

import com.zwl.model.po.Banner;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface BannerMapper {

    List<Banner> getWxBannerList(Banner banner);

    List<Banner> getBannerList(Banner banner);

    int addBanner(Banner banner);

    @Update("update ss_banner set available =0 where id=#{id}")
    int deleteBanner( Integer id);

    int updateBanner(Banner banner);
}


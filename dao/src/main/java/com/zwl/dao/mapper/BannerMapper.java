package com.zwl.dao.mapper;

import com.zwl.model.po.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerMapper {

    List<Banner> getBannerList();

    int insertBanner(Banner banner);

    int  deleteBanner(@Param("id") Long id);

    int  updateBanner(Banner banner);



}

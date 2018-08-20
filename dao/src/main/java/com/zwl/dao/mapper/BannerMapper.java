package com.zwl.dao.mapper;

import com.zwl.model.po.Banner;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface BannerMapper {


    int insert(Banner banner);

    int insertSelective(Banner banner);

    List<Banner> selectBanner(Banner banner);

    @Update("update ss_banner set available =0 where id=#{id}")
    int deleteBanner( Integer id);

    int updateByPrimaryKeySelective(Banner banner);
    int updateByPrimaryKey(Banner banner);
}


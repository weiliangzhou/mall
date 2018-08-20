package com.zwl.dao.mapper;

import com.zwl.model.po.Video;
import com.zwl.model.vo.QueryTypeVideoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface VideoMapper {
    List<Video> getWxVideoList(QueryTypeVideoVo queryTypeVideoVo);

    List<Video> getVideoList(Video video);

    int addVideo(Video video);

    int updateVideo(Video video);

    @Update("update ss_video set available =0 where id=#{id}")
    int deleteVideo(@Param("id") Integer id);

    Video getVideoInfoById(Video video);
}

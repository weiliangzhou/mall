package com.zwl.service;

import com.zwl.model.po.Video;
import com.zwl.model.vo.QueryTypeVideoVo;
import com.zwl.model.vo.VideoVo;

import java.util.List;

public interface VideoService {
    List<Video> getWxVideoList(QueryTypeVideoVo queryTypeVideoVo);

    List<Video> getVideoList(Video video);

    int addVideo(Video video);

    int updateVideo(Video video);

    int deleteVideo(Integer id);

    Video getVideoInfoById(Video video);
}

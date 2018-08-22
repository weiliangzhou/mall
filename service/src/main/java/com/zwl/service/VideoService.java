package com.zwl.service;

import com.zwl.model.po.Video;
import com.zwl.model.vo.QueryTypeVideoVo;

import java.util.List;

public interface VideoService {
    List<Video> getWxVideoList(QueryTypeVideoVo queryTypeVideoVo);

    List<Video> getVideoList(Video video);

    int addVideo(QueryTypeVideoVo queryTypeVideoVo);

    int updateVideo(QueryTypeVideoVo queryTypeVideoVo);

    int deleteVideo(Integer id);

    Video getVideoInfoById(Video video);
}

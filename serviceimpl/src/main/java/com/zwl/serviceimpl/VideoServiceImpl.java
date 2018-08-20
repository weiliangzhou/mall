package com.zwl.serviceimpl;

import com.zwl.dao.mapper.VideoMapper;
import com.zwl.model.po.Video;
import com.zwl.model.vo.QueryTypeVideoVo;
import com.zwl.model.vo.VideoVo;
import com.zwl.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoMapper videoMapper;

    @Override
    public List<Video> getWxVideoList(QueryTypeVideoVo queryTypeVideoVo) {
        return videoMapper.getWxVideoList(queryTypeVideoVo);
    }

    @Override
    public List<Video> getVideoList(Video video) {
        return videoMapper.getVideoList(video);
    }

    @Override
    public int addVideo(Video video) {
        return videoMapper.addVideo(video);
    }

    @Override
    public int updateVideo(Video video) {
        return videoMapper.updateVideo(video);
    }

    @Override
    public int deleteVideo(Integer id) {
        return videoMapper.deleteVideo(id);
    }

    @Override
    public Video getVideoInfoById(Video video) {
        return videoMapper.getVideoInfoById(video);
    }
}

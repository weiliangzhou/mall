package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.Video;
import com.zwl.model.vo.QueryTypeVideoVo;
import com.zwl.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wx/video")
public class VideoController {
    @Autowired
    private VideoService videoService;
    /**
     * 获取视频列表
     * @param jsonObject
     * @return
     */
    @PostMapping("/getVideoList")
    public String getVideoList(@RequestBody JSONObject jsonObject){
        String merchantId=jsonObject.getString("merchantId");
        Integer pageNum=jsonObject.getInteger("pageNum");
        Integer pageSize=jsonObject.getInteger("pageSize");
        Integer queryType = jsonObject.getInteger("queryType");
        PageHelper.startPage(pageNum, pageSize);
        Result result = new Result();
        QueryTypeVideoVo queryTypeVideoVo = new QueryTypeVideoVo();
        queryTypeVideoVo.setMerchantId(merchantId);
        queryTypeVideoVo.setQueryType(queryType);
        List<Video> videoList = videoService.getWxVideoList(queryTypeVideoVo);
        result.setData(videoList);
        return JSON.toJSONString(result);
    }

    @PostMapping("/getVideoInfoById")
    public String getVideoInfoById(@RequestBody JSONObject jsonObject){
        Integer id = jsonObject.getInteger("id");
        Result result = new Result();
        Video video = new Video();
        video.setId(id);
        video = videoService.getVideoInfoById(video);
        result.setData(video);
        return JSON.toJSONString(result);
    }
}

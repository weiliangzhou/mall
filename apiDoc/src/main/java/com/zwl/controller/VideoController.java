package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.Information;
import com.zwl.model.po.Video;
import com.zwl.service.VideoService;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api2Doc(name = "视频管理")
@RestController
public class VideoController {
    @Autowired
    private VideoService videoService;

    @ApiComment(value = "获取视频列表", seeClass =Video.class)
    @RequestMapping(name = "获取视频列表", value = "/teacher/video/getVideoList", method = RequestMethod.POST)
    public Video getVideoList(@ApiComment("pageNum") Integer pageNum, @ApiComment("pageSize") Integer pageSize, @ApiComment("商户号") String merchantId, @ApiComment("标题") String title){
        Video video = new Video();
        return video;
    }

    @ApiComment("视频添加")
    @RequestMapping(name = "视频添加", value = "/teacher/video/add", method = RequestMethod.POST)
    public String addVideo(@ApiComment("Video对象")Video video) {
        Result result = new Result();
        return JSONObject.toJSONString(result);
    }

    @ApiComment("视频编辑")
    @RequestMapping(name = "视频编辑", value = "/teacher/video/update", method = RequestMethod.POST)
    public String updateVideo(@ApiComment("Video对象")Video video) {
        Result result = new Result();
        return JSONObject.toJSONString(result);
    }

    @ApiComment("视频删除")
    @RequestMapping(name = "视频删除", value = "/teacher/video/delete", method = RequestMethod.POST)
    public String deleteVideo(@ApiComment("id") String id){
        Result result = new Result();
        return JSONObject.toJSONString(result);
    }
}

package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.Video;
import com.zwl.model.vo.VideoVo;
import com.zwl.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/teacher/video")
@RestController
@Slf4j
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
        String title = jsonObject.getString("title");
        Integer isRecommend = jsonObject.getInteger("isRecommend");
        Integer isShow = jsonObject.getInteger("isShow");
        Video video = new Video();
        video.setMerchantId(merchantId);
        video.setTitle(title);
        video.setIsRecommend(isRecommend);
        video.setIsShow(isShow);
        Page page = PageHelper.startPage(pageNum, pageSize);
        Result result = new Result();
        List<Video> videoList = videoService.getVideoList(video);
        VideoVo videoVo = new VideoVo();
        videoVo.setPageNum(pageNum);
        videoVo.setTotalPage(page.getTotal());
        videoVo.setVideoList(videoList);
        result.setData(videoVo);
        return JSON.toJSONString(result);
    }

    @PostMapping("/add")
    public String addVideo(@Validated(Update.class) @RequestBody Video video) {
        Result result = new Result();
        int count = videoService.addVideo(video);
        if(1 != count)BSUtil.isTrue(false,"新增失败");
        return JSONObject.toJSONString(result);
    }

    @PostMapping("/update")
    public String updateVideo(@Validated(Update.class) @RequestBody Video video) {
        Result result = new Result();
        int count = videoService.updateVideo(video);
        if(1 != count)BSUtil.isTrue(false,"修改失败");
        return JSONObject.toJSONString(result);
    }

    @PostMapping("/delete")
    public String deleteVideo(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        Integer id = jsonObject.getInteger("id");
        int count = videoService.deleteVideo(id);
        if(1 != count)BSUtil.isTrue(false,"删除失败");
        return JSONObject.toJSONString(result);
    }
}

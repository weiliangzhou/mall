package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.baseresult.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 二师兄超级帅
 * @Title: FileUploadController
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/2220:35
 */
@ApiComment("老师后台上传图片")
public class FileUploadController {
    @ApiComment("上传图片")
    @RequestMapping(name = "上传图片",
            value = "/teacher/image/upload", method = RequestMethod.POST)
    public String imageUpload(@ApiComment("文件") MultipartFile file) {
        Result result = new Result();
        return JSON.toJSONString(result);
    }
}

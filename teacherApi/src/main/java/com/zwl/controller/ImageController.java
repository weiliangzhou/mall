package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.zwl.model.baseresult.Result;
import com.zwl.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 二师兄超级帅
 * @Title: ImageController
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1919:12
 */
@RestController
@RequestMapping("/teacher/image")
public class ImageController {
    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    public String imageUpload(@RequestParam("file") MultipartFile file) {
        Result result = new Result();
        String url = fileUploadService.upload(file, 1);
        result.setData(url);
        return JSON.toJSONString(result);
    }
}

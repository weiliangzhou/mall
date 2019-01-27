package com.zwl.controller;

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
@RequestMapping("/wx/file")
public class ImageController extends BannerController {
    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    public String imageUpload(@RequestParam("file") MultipartFile file) {
        String url = fileUploadService.upload(file, 1);
        return setSuccessResult(url);
    }
}

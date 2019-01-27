package com.zwl.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 二师兄超级帅
 * @Title: FileUploadService
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1810:09
 */
public interface FileUploadService {
    public String upload(MultipartFile file,int type);
}

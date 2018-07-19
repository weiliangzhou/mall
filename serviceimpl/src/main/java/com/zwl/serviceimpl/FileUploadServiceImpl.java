package com.zwl.serviceimpl;

import com.zwl.model.exception.BSUtil;
import com.zwl.service.FileUploadService;
import com.zwl.util.AliOSSUtil;
import com.zwl.util.FileCheckUtil;
import com.zwl.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author 二师兄超级帅
 * @Title: FileUploadServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1810:10
 */
@Service
@Slf4j
public class FileUploadServiceImpl implements FileUploadService {
    @Override
    public String upload(MultipartFile file, int type) {
        if (file.isEmpty())
            BSUtil.isTrue(false, "文件不能为空");

        // 上传文件信息
        log.info("OriginalFilename：" + file.getOriginalFilename());
        log.info("ContentType：" + file.getContentType());
        log.info("Name：" + file.getName());
        log.info("Size：" + file.getSize());


        //TODO:文件大小、名称、类型检查的业务处理
        if (1 == type) {
            if (file.getSize() > 3145728)
                BSUtil.isTrue(false, "上传图片大小不能超过3M");

            if (!FileCheckUtil.isImageType(file.getContentType()))
                BSUtil.isTrue(false, "请上传正确的图片类型");
        }


        try {
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 获取文件后缀
            String prefix = fileName.substring(fileName.lastIndexOf("."));
            // 用uuid作为文件名，防止生成的临时文件重复
            final File excelFile = File.createTempFile(UUIDUtil.getUUID32(), prefix);
            // MultipartFile to File
            file.transferTo(excelFile);

            //你的业务逻辑

            // 上传到OSS
            String url = AliOSSUtil.uploadLocalFile(excelFile, "upload/image/");
            if (url == null)
                BSUtil.isTrue(false, "上传oss失败");
            log.info("上传完毕,访问地址:" + url);

            //程序结束时，删除临时文件
            deleteFile(excelFile);
            return url;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 删除
     *
     * @param files
     */
    private void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

}

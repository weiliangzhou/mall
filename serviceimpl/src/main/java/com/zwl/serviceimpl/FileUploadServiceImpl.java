package com.zwl.serviceimpl;

import com.zwl.model.exception.BSUtil;
import com.zwl.service.FileUploadService;
import com.zwl.util.AliOSSUtil;
import com.zwl.util.FileCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

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
    //    windows
//    private static String uploadPath = "F:\\sass\\image\\";
    //    linux
    private static String uploadPath = "/home/upload";

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
            // 检查上传目录
            File targetFile = new File(uploadPath);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }

            // 实例化输出流
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(uploadPath + file.getOriginalFilename()));
            out.write(file.getBytes());
            out.flush();
            out.close();

            // 上传到OSS
            String url = AliOSSUtil.uploadLocalFile(new File(uploadPath + file.getOriginalFilename()), "upload/image/");
            if (url == null)
                BSUtil.isTrue(false, "上传oss失败");
            log.info("上传完毕,访问地址:" + url);

            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

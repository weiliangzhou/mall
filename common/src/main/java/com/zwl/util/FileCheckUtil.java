package com.zwl.util;

public class FileCheckUtil {
    /**
     * 获取文件的后缀名(带.jpe  .word 等)
     *
     * @return
     */
    public static String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 是否是图片类型
     */
    public static boolean isImageType(String contentType) {
        if ("image/jpeg".equals(contentType)||"image/png".equals(contentType)
                ||"image/gif".equals(contentType)||"image/bmp".equals(contentType))
            return true;
        else
            return false;
    }

}

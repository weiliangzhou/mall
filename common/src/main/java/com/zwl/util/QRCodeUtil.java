package com.zwl.util;

/**
 * 二维码生成工具类
 *
 * @author 二师兄超级帅
 * @data 2018/09/08
 * QRCode
 */

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class QRCodeUtil {
    public static void main(String[] args) {
        String url = "http://dy.xc2018.com.cn/mine?referrer=c5530f7e341b4ae9afc5d50cc69a212c";
//        String path = FileSystemView.getFileSystemView().getHomeDirectory() + File.separator + "testQrcode";
//        String fileName = "temp.jpg";
        System.out.println(createQrCode(url, null, null));
    }

    public static String createQrCode(String url, String path, String fileName) {
        String imgUrl = "";
        try {
            Map<EncodeHintType, String> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 400, 400, hints);
//            File file = new File(path, fileName);
//            if (file.exists() || ((file.getParentFile().exists() || file.getParentFile().mkdirs()) && file.createNewFile())) {
//                writeToFile(bitMatrix, "jpg", file);
//                System.out.println("搞定：" + file);
//            }
            BufferedImage image = toBufferedImage(bitMatrix);
            byte[] imgByte = ImageUtil.imageToBytes(image, "png");
            imgUrl = AliOSSUtil.uploadFileByte(imgByte);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgUrl;
    }

    static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }

    static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

}
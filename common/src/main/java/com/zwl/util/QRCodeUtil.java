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
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class QRCodeUtil {
    private static final int WIDTH = 200; // 二维码宽
    private static final int HEIGHT = 200; // 二维码高
    private static final int QRCOLOR = 0xFF000000; // 默认是黑色
    private static final int BGWHITE = 0xFFFFFFFF; // 背景颜色


    public static void main(String[] args) {
        String url = "http://dy.xc2018.com.cn/mine?referrer=c5530f7e341b4ae9afc5d50cc69a212c";
//        String path = FileSystemView.getFileSystemView().getHomeDirectory() + File.separator + "testQrcode";
//        String fileName = "temp.jpg";
        String smallImage = createQrCode(url, null, null);
        try {
            String url1 = mergeImage("http://chuang-saas.oss-cn-hangzhou.aliyuncs.com/upload/image/20180911/48c31d7eaa084fb4bc62ea98b0e1af24.png", smallImage, "310", "380","https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoESgDChRh1CibJojpCiaLZtmUiaa1x5EvbW0zvvOOgBCj2kmsia5xuFCEiczia21ribLic8e3ck7fVtxIqOQ/132", "200", "75");
            System.out.println(url1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String createQrCode(String url, String path, String fileName) {
        String imgUrl = "";
        try {
            Map<EncodeHintType, String> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 200, 200, hints);
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


    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? QRCOLOR : BGWHITE);
            }
        }
        return image;
    }

    // 生成带logo的二维码图片
    public static String drawLogoQRCode(String logoUrl, String qrUrl) {
        try {
            Map<EncodeHintType, String> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
            BitMatrix bm = new MultiFormatWriter().encode(qrUrl, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
            BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

            // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    image.setRGB(x, y, bm.get(x, y) ? QRCOLOR : BGWHITE);
                }
            }
            int width = image.getWidth();
            int height = image.getHeight();
            if (StringUtils.isBlank(logoUrl)) {
                // 构建绘图对象
                Graphics2D g = image.createGraphics();
                // 读取Logo图片
                BufferedImage logo = ImageIO.read(new File(logoUrl));
                // 开始绘制logo图片
                g.drawImage(logo, width * 2 / 5, height * 2 / 5, width * 2 / 10, height * 2 / 10, null);
                g.dispose();
                logo.flush();
            }

            image.flush();
            byte[] imgByte = ImageUtil.imageToBytes(image, "png");
            return AliOSSUtil.uploadFileByte(imgByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String mergeImage(String bigImage, String smallImage, String x, String y, String userLogoUrl, String x1, String y1) throws IOException {

        try {
            BufferedImage small;
            BufferedImage userLogo;
            BufferedImage big;
            if (bigImage.contains("http")) {
                URL url = new URL(bigImage);
                big = ImageIO.read(url);
            } else {
                big = ImageIO.read(new File(bigImage));
            }
            if (smallImage.contains("http")) {
                URL url = new URL(smallImage);
                small = ImageIO.read(url);
            } else {
                small = ImageIO.read(new File(smallImage));
            }
            if (userLogoUrl.contains("http")) {
                URL url = new URL(userLogoUrl);
                userLogo = ImageIO.read(url);
            } else {
                userLogo = ImageIO.read(new File(userLogoUrl));
            }

            Graphics2D g = big.createGraphics();
            float fx = Float.parseFloat(x);
            float fy = Float.parseFloat(y);
            int x_i = (int) fx;
            int y_i = (int) fy;

            float fx_t = Float.parseFloat(x1);
            float fy_t = Float.parseFloat(y1);
            int x_i_t = (int) fx_t;
            int y_i_t  = (int) fy_t;
            g.drawImage(small, x_i, y_i, small.getWidth(), small.getHeight(), null);
            g.drawImage(userLogo, x_i_t, y_i_t, userLogo.getWidth(), userLogo.getHeight(), null);
            g.dispose();
            byte[] imgByte = ImageUtil.imageToBytes(big, "png");
            return AliOSSUtil.uploadFileByte(imgByte);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}
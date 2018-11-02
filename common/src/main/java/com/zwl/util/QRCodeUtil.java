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
import java.awt.geom.RoundRectangle2D;
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


    public static void main(String[] args) throws IOException {
        String url = "http://dy.xc2018.com.cn/home?referrer=c5530f7e341b4ae9afc5d50cc69a212c";
//        String path = FileSystemView.getFileSystemView().getHomeDirectory() + File.separator + "testQrcode";
//        String fileName = "temp.jpg";
//        String smallImage = createQrCode(url, null, null);
//        try {
//            String url1 = mergeImage("http://chuang-saas.oss-cn-hangzhou.aliyuncs.com/upload/image/20180911/48c31d7eaa084fb4bc62ea98b0e1af24.png", smallImage, "310", "380","https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoESgDChRh1CibJojpCiaLZtmUiaa1x5EvbW0zvvOOgBCj2kmsia5xuFCEiczia21ribLic8e3ck7fVtxIqOQ/132", "200", "75","二师兄");
//            System.out.println(url1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        makeCircularImg("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83erRYquBqibVusZXLAmBUTk9d4GXk9C4UkWq5RmGJ94CI4s3npxCs5mNNFnYxIYnRYprZtPo8ib03cYw/132", "d:/11.jpg", 140, 150);

    }

    public static String makeCircularImg(String srcFilePath, String circularImgSavePath, int targetSize, int cornerRadius) throws IOException {
        URL url = new URL(srcFilePath);
        BufferedImage bufferedImage = ImageIO.read(url);
        BufferedImage circularBufferImage = roundImage(bufferedImage, targetSize, cornerRadius);
        ImageIO.write(circularBufferImage, "png", new File(circularImgSavePath));
        return circularImgSavePath;
    }


    public static String createQrCode(String url, String path, String fileName) {
        String imgUrl = "";
        try {
            Map<EncodeHintType, String> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 300, 300, hints);
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

    public static String mergeImage(String bigImage, String smallImage, int smallImageX, int smallImageY, String userLogoUrl, int userLogoUrlX, int userLogoUrlY, String nickName, int nickNameX, int nickNameY, Color nickNameColor) throws IOException {

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
            g.drawImage(small, (big.getWidth() - small.getWidth()) / 2, smallImageY, small.getWidth(), small.getHeight(), null);
            g.drawImage(roundImage(userLogo, 130, 150), (big.getWidth() - userLogo.getWidth()) / 2, userLogoUrlY, roundImage(userLogo, 130, 150).getWidth(), roundImage(userLogo, 130, 150).getHeight(), null);
            Font font = new Font("宋体", Font.BOLD, 20);
            g.setFont(font);
            g.setColor(nickNameColor);
            // 抗锯齿
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // 计算文字长度，计算居中的x点坐标
            FontMetrics fm = g.getFontMetrics(font);
            int textWidth = fm.stringWidth(nickName);
            int widthX = (big.getWidth() - textWidth) / 2;
            // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
            g.drawString(nickName, widthX, nickNameY);
            g.dispose();
            byte[] imgByte = ImageUtil.imageToBytes(big, "png");
            return AliOSSUtil.uploadFileByte(imgByte);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String SlMergeImage(String bigImage, String smallImage, int smallImageX, int smallImageY, String userLogoUrl, int userLogoUrlX, int userLogoUrlY, String nickName, int nickNameX, int nickNameY, Color nickNameColor, String slName, Integer slNameX, Integer slNameY, Color slNameColor, String slTime, Integer slTimeX, Integer slTimeY, Color slTimeColor) throws IOException {
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
            g.drawImage(small, (big.getWidth() - 180) / 2, smallImageY, 180, 180, null);
            g.drawImage(roundImage(userLogo, 130, 150), (big.getWidth() - 210) / 2, userLogoUrlY, 210, 210, null);

            Font font = new Font("宋体", Font.BOLD, 30);
            g.setFont(font);
            g.setColor(nickNameColor);
            // 抗锯齿
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // 计算文字长度，计算居中的x点坐标
            FontMetrics fm = g.getFontMetrics(font);
            int textWidth = fm.stringWidth(nickName);
            int widthX = (big.getWidth() - textWidth) / 2;
            // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
            g.drawString(nickName, widthX, nickNameY);


            Font slNameFont = new Font("宋体", Font.BOLD, 30);
            g.setFont(slNameFont);
            g.setColor(slNameColor);
            // 抗锯齿
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // 计算文字长度，计算居中的x点坐标
            FontMetrics slNameFm = g.getFontMetrics(slNameFont);
            int slNameWidth = slNameFm.stringWidth(slName);
            int slNameMidX = (big.getWidth() - slNameWidth) / 2;
            // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
            g.drawString(slName, slNameMidX, slNameY);


            Font slTimeFont = new Font("宋体", Font.BOLD, 20);
            g.setFont(slTimeFont);
            g.setColor(slTimeColor);
            // 抗锯齿
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // 计算文字长度，计算居中的x点坐标
            FontMetrics slTimeFm = g.getFontMetrics(slTimeFont);
            int slTimeWidth = slTimeFm.stringWidth(slTime);
            int slTimeMidX = (big.getWidth() - slTimeWidth) / 2;
            // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
            g.drawString(slTime, slTimeMidX, slTimeY);

            g.dispose();
            byte[] imgByte = ImageUtil.imageToBytes(big, "png");
            return AliOSSUtil.uploadFileByte(imgByte);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String giftMergeImage(String bigImage,
                                        String smallImage, int smallImageX, int smallImageY,
                                        String userLogoUrl, int userLogoUrlX, int userLogoUrlY,
                                        String nickName, int nickNameX, int nickNameY, Color nickNameColor,
                                        String memberLevel, Integer memberLevelX, Integer memberLevelY, Color memberLevelColor,
                                        String giftMainImg, Integer giftMainX, Integer giftMainY) throws IOException {
        try {
            BufferedImage small;
            BufferedImage userLogo;
            BufferedImage big;
            BufferedImage giftMainImg_temp;
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
            if (giftMainImg.contains("http")) {
                URL url = new URL(giftMainImg);
                giftMainImg_temp = ImageIO.read(url);
            } else {
                giftMainImg_temp = ImageIO.read(new File(giftMainImg));
            }
            Graphics2D g = big.createGraphics();
            g.drawImage(small, smallImageX, smallImageY, 120, 120, null);
            g.drawImage(giftMainImg_temp, giftMainX, giftMainY, 394, 230, null);
            g.drawImage(roundImage(userLogo, 130, 150), userLogoUrlX, userLogoUrlY, 100, 100, null);
            Font font = new Font("宋体", Font.BOLD, 20);
            g.setFont(font);
            g.setColor(nickNameColor);
            // 抗锯齿
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
            g.drawString(nickName, nickNameX, nickNameY);


            Font memberLevelFont = new Font("宋体", Font.BOLD, 16);
            g.setFont(memberLevelFont);
            g.setColor(memberLevelColor);
            // 抗锯齿
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
            g.drawString(memberLevel, memberLevelX, memberLevelY);


            g.dispose();
            byte[] imgByte = ImageUtil.imageToBytes(big, "png");
            return AliOSSUtil.uploadFileByte(imgByte);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    private static BufferedImage roundImage(BufferedImage image, int targetSize, int cornerRadius) {
        BufferedImage outputImage = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = outputImage.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, targetSize, targetSize, cornerRadius, cornerRadius));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return outputImage;
    }


}
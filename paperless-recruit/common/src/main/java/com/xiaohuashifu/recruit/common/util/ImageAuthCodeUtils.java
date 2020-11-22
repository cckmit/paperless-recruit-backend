package com.xiaohuashifu.recruit.common.util;

import org.apache.axis.encoding.Base64;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * 描述：生成图形验证码的工具类
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/22 16:14
 */
public class ImageAuthCodeUtils {

    /**
     * 该方法用于生成图形验证码
     *
     * @param width 验证码宽度
     * @param height 验证码高度
     * @param length 验证码字符串长度
     * @return 验证码图片的Base64编码和验证码
     * @throws IOException .
     */
    public static ImageAuthCode createImageCode(int width, int height, int length) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        g.setColor(getRandColor(160, 200));
        Random random = new Random();
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        StringBuilder sRand = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand.append(rand);
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
        }
        g.dispose();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", stream);
        String base64Image = Base64.encode(stream.toByteArray());

        return new ImageAuthCode(base64Image, sRand.toString());
    }

    private static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    public static class ImageAuthCode {

        /**
         * base64形式编码的图形验证码
         */
        private String base64Image;

        /**
         * 图形验证码里面的验证码字符串
         */
        private String authCode;

        public ImageAuthCode(String base64Image, String authCode) {
            this.base64Image = base64Image;
            this.authCode = authCode;
        }

        public String getBase64Image() {
            return base64Image;
        }

        public void setBase64Image(String base64Image) {
            this.base64Image = base64Image;
        }

        public String getAuthCode() {
            return authCode;
        }

        public void setAuthCode(String authCode) {
            this.authCode = authCode;
        }

        @Override
        public String toString() {
            return "ImageAuthCode{" +
                    "base64Image='" + base64Image + '\'' +
                    ", authCode='" + authCode + '\'' +
                    '}';
        }
    }
}

package com.xiaohuashifu.recruit.common.util;


import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 描述：DES加密工具类
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/20 20:47
 */
public class DesUtils {

    /**
     * 加密
     *
     * @param data 数据
     * @param secretKey 密钥
     * @return 加密后数据
     * @throws Exception .
     */
    public static String encrypt(String data, String secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKeyFactory(secretKey));
        return new String(Base64.encodeBase64(cipher.doFinal(data.getBytes())));
    }

    /**
     * 解密
     *
     * @param data 数据
     * @param secretKey 密钥
     * @return 解密后数据
     * @throws Exception .
     */
    public static String decrypt(String data, String secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, getSecretKeyFactory(secretKey));
        return new String(cipher.doFinal(Base64.decodeBase64(data.getBytes())));
    }

    /**
     * 获取密钥对象
     *
     * @return SecretKey 密钥
     * @throws Exception .
     */
    private static SecretKey getSecretKeyFactory(String secretKey) throws Exception {
        SecretKeyFactory des = SecretKeyFactory.getInstance("DES");
        return des.generateSecret(new DESKeySpec(secretKey.getBytes()));
    }

}

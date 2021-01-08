package com.xiaohuashifu.recruit.common.util;

import java.math.BigDecimal;

/**
 * 描述：人民币相关的函数
 *
 * @author xhsf
 * @create 2021/1/8 15:58
 */
public class RmbUtils {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

    /**
     * 分转元
     *
     * @param fen 分
     * @return 元
     */
    public static String fen2Yuan(int fen) {
        return new BigDecimal(fen).divide(ONE_HUNDRED).toString();
    }

    /**
     * 元转分
     *
     * @param yuan 元
     * @return 分
     */
    public static int yuan2Fen(String yuan) {
        return new BigDecimal(yuan).multiply(ONE_HUNDRED).intValue();
    }

}

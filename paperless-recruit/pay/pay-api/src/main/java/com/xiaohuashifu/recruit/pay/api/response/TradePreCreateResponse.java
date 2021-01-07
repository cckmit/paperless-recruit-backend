package com.xiaohuashifu.recruit.pay.api.response;

import com.xiaohuashifu.recruit.common.response.Response;
import com.xiaohuashifu.recruit.pay.api.domain.QrCode;
import lombok.Builder;
import lombok.Data;

/**
 * 描述：预下单响应
 *
 * @author xhsf
 * @create 2021/1/7 16:55
 */
@Data
@Builder
public class TradePreCreateResponse implements Response {

    /**
     * 二维码
     */
    private final QrCode qrCode;

}

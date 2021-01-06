package com.xiaohuashifu.recruit.pay.service.manager.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.xiaohuashifu.recruit.pay.api.request.PreCreateTraceRequest;
import com.xiaohuashifu.recruit.pay.service.manager.AlipayManager;
import org.springframework.stereotype.Component;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2021/1/6 19:10
 */
@Component
public class AlipayManagerImpl implements AlipayManager {

    private final AlipayClient alipayClient;

    public AlipayManagerImpl(AlipayClient alipayClient) {
        this.alipayClient = alipayClient;
    }

    public Object preCreate(PreCreateTraceRequest preCreateRequest) {
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel alipayTradePrecreateModel = new AlipayTradePrecreateModel();
        alipayTradePrecreateModel.setOutTradeNo("20150320010101003");
        alipayTradePrecreateModel.setTotalAmount("333.33");
        alipayTradePrecreateModel.setSubject("Iphone6 16G");
        alipayTradePrecreateModel.setTimeoutExpress("90m");
        request.setBizModel(alipayTradePrecreateModel);
        AlipayTradePrecreateResponse response = null;
        try {
            response = alipayClient.certificateExecute(request);
            System.out.print(response.getBody());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return response;
    }

}

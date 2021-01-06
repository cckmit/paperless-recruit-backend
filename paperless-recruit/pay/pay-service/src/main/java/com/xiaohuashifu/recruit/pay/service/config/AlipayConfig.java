package com.xiaohuashifu.recruit.pay.service.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述：Alipay 配置类
 *
 * @author xhsf
 * @create 2021/1/6 19:31
 */
@Configuration
public class AlipayConfig {

    /**
     * alipay  客户端
     *
     * @param certAlipayRequest CertAlipayRequest
     * @return AlipayClient
     */
    @Bean
    public AlipayClient alipayClient(CertAlipayRequest certAlipayRequest) throws AlipayApiException {
        return new DefaultAlipayClient(certAlipayRequest);
    }

    /**
     * alipay 配置
     *
     * @param privateKey 私钥
     * @param appId 应用 id
     * @param certPath 应用公钥证书文件路径
     * @param alipayPublicCertPath 支付宝公钥证书文件路径
     * @param rootCertPath 支付宝根证书文件路径
     * @return CertAlipayRequest
     */
    @Bean
    public CertAlipayRequest certAlipayRequest(@Value("${alipay.private-key}") String privateKey,
                                               @Value("${alipay.app-id}") String appId,
                                               @Value("${alipay.cert-path}") String certPath,
                                               @Value("${alipay.alipay-public-cert-path}") String alipayPublicCertPath,
                                               @Value("${alipay.root-cert-path}") String rootCertPath) {
        CertAlipayRequest certParams = new CertAlipayRequest();
        certParams.setServerUrl("https://openapi.alipaydev.com/gateway.do");
        certParams.setAppId(appId);
        certParams.setPrivateKey(privateKey);
        certParams.setCharset("utf-8");
        certParams.setFormat("json");
        certParams.setSignType("RSA2");
        // 应用公钥证书文件路径
        certParams.setCertPath(certPath);
        // 支付宝公钥证书文件路径
        certParams.setAlipayPublicCertPath(alipayPublicCertPath);
        // 支付宝根证书文件路径
        certParams.setRootCertPath(rootCertPath);
        return certParams;
    }

}

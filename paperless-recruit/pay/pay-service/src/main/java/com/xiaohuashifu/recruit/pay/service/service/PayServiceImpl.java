//package com.xiaohuashifu.recruit.pay.service.service;
//
//import com.alibaba.fastjson.JSONObject;
//import com.alipay.api.AlipayClient;
//import com.alipay.api.CertAlipayRequest;
//import com.alipay.api.DefaultAlipayClient;
//import com.alipay.api.domain.AlipayOpenOperationOpenbizmockBizQueryModel;
//import com.alipay.api.request.AlipayOpenOperationOpenbizmockBizQueryRequest;
//import com.alipay.api.request.AlipayTradePrecreateRequest;
//import com.alipay.api.response.AlipayOpenOperationOpenbizmockBizQueryResponse;
//import com.alipay.api.response.AlipayTradePrecreateResponse;
//
///**
// * 描述：
// *
// * @author xhsf
// * @create 2021/1/6 15:45
// */
//public class PayServiceImpl {
//    public static void main(String[] args) {
//        try {
//            // 1. 创建AlipayClient实例
//            AlipayClient alipayClient = new DefaultAlipayClient(getClientParams());
//            // 2. 创建使用的Open API对应的Request请求对象
//            AlipayOpenOperationOpenbizmockBizQueryRequest request = getRequest();
//            // 3. 发起请求并处理响应
//            AlipayOpenOperationOpenbizmockBizQueryResponse response = alipayClient.certificateExecute(request);
//            if (response.isSuccess()) {
//                System.out.println("调用成功。");
//            } else {
//                System.out.println("调用失败，原因：" + response.getMsg() + "，" + response.getSubMsg());
//            }
//        } catch (Exception e) {
//            System.out.println("调用遭遇异常，原因：" + e.getMessage());
//            throw new RuntimeException(e.getMessage(), e);
//        }
//    }
//
//    private static CertAlipayRequest getClientParams() {
//        CertAlipayRequest certParams = new CertAlipayRequest();
//        certParams.setServerUrl("https://openapi.alipay.com/gateway.do");
//        //请更换为您的AppId
//        certParams.setAppId("2021002122668011");
//        //请更换为您的PKCS8格式的应用私钥
//        certParams.setPrivateKey("MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCblUybKK2t/u5hU4FeW09rgmLCZscAOaLx6NVQjykkVwlguxrLGv1VH1+LULo7e2JINdGpu7dgikhhBsi3oGena2B0TZEq478uafHS/dz4mWYi72PIKLX1Vh3klXlWRcbKb/kj1f/ahafe0leqWXU7nqS9Ub5FLs86tIYRmrrowz+9nxbFf+oxcGOYvnqqj+I91PbSqAyDrFOD0BX5rDNZNJKYTvxQJKEjffJPtskJcF8TSMnIswIGLysWcfJQH/0e6t6v5JRyqfGQitaQVWtvm9NG3BjsF8P4zNd8lKXuHoeFbSBB0wYUwiZ5XezRTid5XciJbQ8j+cMSrcWor5t/AgMBAAECggEAI5JXGYKLcyS+S/JTuccWuXXuLpRhG2QSJU0euHSC65QayMr+rUFWeofZyHiEm+Tx66CidzQ8EQ6MzeJHMfSyvkyGmRDDCBNaOrpuV9dDFnO8LnCdV743HAi8fO7P9zGs6cwAInljLgsw/nkJIiGh9Cepcr+lyRuStdFgddXythTSHjJ/0G0vMYv4dlAUHrFXsqUzevt0pPHg/v5SX5leQ8PON28df7Zztby855N+E6aM3HuSe3BAEAvv6wxPG6N5SRIiMclpTmM2Dqh1S+iXrbs0oO1KL8p7jPe5n1uSxAZWCCXVwGrTBrjy/cyMv6dd6GvtAhvZWDVvsu0FjqacAQKBgQDiCLbtBYalmJZ1Uj8jfIqFnY7JBN5HN0Rahg6DwoOfH1DQ9oSy6emGaDmO6EdLFp/SBzh8gNitm8GP3EVOg2WfjliaFQNLSL02mtXFBjL/uOIXRT68pZPTUCTvioo22lb3LO6neyNK39KFm/gGswt5ZeyXQWU/oC3jNfY0G9w1vwKBgQCwNZRpqm3/mIYctQ/E0UJZJBX2lRWiTUBe8F+Mgq2asvO2x2wqzaElLynT0TfnV73RMryJBr81HHPk6iUV7Xyjmn2S2uvB2tjV/YX7u6X7QWfSwtigQT3nPMKVIjMgxFmjgbR+9N/Iky09yzpBNhvnnxjOFf3GJr0dXJFtRHOKQQKBgQDSNrSZXuhInc5gMiuSP0M83LlsbmCi7t1dKwvYslBi7mbk0D+9fRm37LqLE68qQFJGrHx2HgZl/1NXDc5PJJigeWwhcKVtoKqIXBuvbwKDHVj3t2iPXZZLkl+nf7rnJMx4eItDq5u5CMvrgpB6+o0TTB1q1mDpeXjztL3S3I5CVQKBgGL3NMvD9NPBDlHBP4IDJYh7Q/qh8OyfBrBLwN5h5q8kIs1dY9sGcqM3wf49W3CLTwjXvhwXtfxWZqEDAKE4KPCaLDz2x8Kw72gAY+fmlGpxaaZt2Mzo16Ov9HleI+kbXlz+1CkZzzYPszEpmyZVBYcpS/0zTLyltUFwZB1CfMIBAoGBAMGC/3/ptJHLddE21GG+4fxfbIDKU7fVl/llv38jqWwFnCd36vjCiLtXDHV8eewTPr8kBYHOSXMxfc4faPBToR8xBKrZ30LWvHqI1jCjmZD56AjUUELBsmfnBumxHHFHKoASrQfqRN2qfyQ9sHHxKkNCuYMIT9ehdJTmz+fmL06d");
//        //请更换为您使用的字符集编码，推荐采用utf-8
//        certParams.setCharset("utf-8");
//        certParams.setFormat("json");
//        certParams.setSignType("RSA2");
//        //请更换为您的应用公钥证书文件路径
//        certParams.setCertPath("D:\\Github\\paperless-recruit\\paperless-recruit-doc\\账号密码\\支付宝\\appCertPublicKey_2021002122668011.crt");
//        //请更换您的支付宝公钥证书文件路径
//        certParams.setAlipayPublicCertPath("/home/foo/alipayCertPublicKey_RSA2.crt");
//        //更换为支付宝根证书文件路径
//        certParams.setRootCertPath("/home/foo/alipayRootCert.crt");
//        return certParams;
//    }
//
//    private static AlipayOpenOperationOpenbizmockBizQueryRequest getRequest() {
//        // 初始化Request，并填充Model属性。实际调用时请替换为您想要使用的API对应的Request对象。
//        AlipayOpenOperationOpenbizmockBizQueryRequest request = new AlipayOpenOperationOpenbizmockBizQueryRequest();
//        AlipayOpenOperationOpenbizmockBizQueryModel model = new AlipayOpenOperationOpenbizmockBizQueryModel();
//        model.setBizNo("test");
//        request.setBizModel(model);
//        return request;
//    }
//}

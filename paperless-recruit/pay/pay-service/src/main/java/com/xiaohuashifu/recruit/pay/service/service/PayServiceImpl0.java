//package com.xiaohuashifu.recruit.pay.service.service;
//
//import com.alipay.easysdk.factory.Factory;
//import com.alipay.easysdk.kernel.Config;
//import com.alipay.easysdk.kernel.util.ResponseChecker;
//import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
//import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
//
///**
// * 描述：
// *
// * @author xhsf
// * @create 2021/1/6 15:45
// */
//public class PayServiceImpl0 {
//    public static void main(String[] args) throws Exception {
//        // 1. 设置参数（全局只需设置一次）
//        Factory.setOptions(getOptions());
//        try {
//            // 2. 发起API调用（以创建当面付收款二维码为例）
//            AlipayTradePrecreateResponse response = Factory.Payment.FaceToFace()
//                    .preCreate("Apple iPhone11 128G", "2234567894", "0.01");
//            // 3. 处理响应或异常
//            if (ResponseChecker.success(response)) {
//                System.out.println(response.getQrCode());
//                System.out.println("调用成功");
//                AlipayTradeQueryResponse queryResponse = Factory.Payment.Common().query("2234567894");
//                System.out.println(queryResponse.getBody());
//            } else {
//                System.err.println("调用失败，原因：" + response.msg + "，" + response.subMsg);
//            }
//        } catch (Exception e) {
//            System.err.println("调用遭遇异常，原因：" + e.getMessage());
//            throw new RuntimeException(e.getMessage(), e);
//        }
//    }
//
//    private static Config getOptions() {
//        Config config = new Config();
//        config.protocol = "https";
//        config.gatewayHost = "openapi.alipay.com";
//        config.signType = "RSA2";
//
//        config.appId = "2021002122606026";
//
//        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
//        config.merchantPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCblUybKK2t/u5hU4FeW09rgmLCZscAOaLx6NVQjykkVwlguxrLGv1VH1+LULo7e2JINdGpu7dgikhhBsi3oGena2B0TZEq478uafHS/dz4mWYi72PIKLX1Vh3klXlWRcbKb/kj1f/ahafe0leqWXU7nqS9Ub5FLs86tIYRmrrowz+9nxbFf+oxcGOYvnqqj+I91PbSqAyDrFOD0BX5rDNZNJKYTvxQJKEjffJPtskJcF8TSMnIswIGLysWcfJQH/0e6t6v5JRyqfGQitaQVWtvm9NG3BjsF8P4zNd8lKXuHoeFbSBB0wYUwiZ5XezRTid5XciJbQ8j+cMSrcWor5t/AgMBAAECggEAI5JXGYKLcyS+S/JTuccWuXXuLpRhG2QSJU0euHSC65QayMr+rUFWeofZyHiEm+Tx66CidzQ8EQ6MzeJHMfSyvkyGmRDDCBNaOrpuV9dDFnO8LnCdV743HAi8fO7P9zGs6cwAInljLgsw/nkJIiGh9Cepcr+lyRuStdFgddXythTSHjJ/0G0vMYv4dlAUHrFXsqUzevt0pPHg/v5SX5leQ8PON28df7Zztby855N+E6aM3HuSe3BAEAvv6wxPG6N5SRIiMclpTmM2Dqh1S+iXrbs0oO1KL8p7jPe5n1uSxAZWCCXVwGrTBrjy/cyMv6dd6GvtAhvZWDVvsu0FjqacAQKBgQDiCLbtBYalmJZ1Uj8jfIqFnY7JBN5HN0Rahg6DwoOfH1DQ9oSy6emGaDmO6EdLFp/SBzh8gNitm8GP3EVOg2WfjliaFQNLSL02mtXFBjL/uOIXRT68pZPTUCTvioo22lb3LO6neyNK39KFm/gGswt5ZeyXQWU/oC3jNfY0G9w1vwKBgQCwNZRpqm3/mIYctQ/E0UJZJBX2lRWiTUBe8F+Mgq2asvO2x2wqzaElLynT0TfnV73RMryJBr81HHPk6iUV7Xyjmn2S2uvB2tjV/YX7u6X7QWfSwtigQT3nPMKVIjMgxFmjgbR+9N/Iky09yzpBNhvnnxjOFf3GJr0dXJFtRHOKQQKBgQDSNrSZXuhInc5gMiuSP0M83LlsbmCi7t1dKwvYslBi7mbk0D+9fRm37LqLE68qQFJGrHx2HgZl/1NXDc5PJJigeWwhcKVtoKqIXBuvbwKDHVj3t2iPXZZLkl+nf7rnJMx4eItDq5u5CMvrgpB6+o0TTB1q1mDpeXjztL3S3I5CVQKBgGL3NMvD9NPBDlHBP4IDJYh7Q/qh8OyfBrBLwN5h5q8kIs1dY9sGcqM3wf49W3CLTwjXvhwXtfxWZqEDAKE4KPCaLDz2x8Kw72gAY+fmlGpxaaZt2Mzo16Ov9HleI+kbXlz+1CkZzzYPszEpmyZVBYcpS/0zTLyltUFwZB1CfMIBAoGBAMGC/3/ptJHLddE21GG+4fxfbIDKU7fVl/llv38jqWwFnCd36vjCiLtXDHV8eewTPr8kBYHOSXMxfc4faPBToR8xBKrZ30LWvHqI1jCjmZD56AjUUELBsmfnBumxHHFHKoASrQfqRN2qfyQ9sHHxKkNCuYMIT9ehdJTmz+fmL06d";
//
//        //注：证书文件路径支持设置为文件系统中的路径或CLASS_PATH中的路径，优先从文件系统中加载，加载失败后会继续尝试从CLASS_PATH中加载
//        config.merchantCertPath = "D:\\Github\\paperless-recruit\\paperless-recruit-doc\\账号密码\\支付宝1\\appCertPublicKey_2021002122606026.crt";
//        config.alipayCertPath = "D:\\Github\\paperless-recruit\\paperless-recruit-doc\\账号密码\\支付宝1\\alipayCertPublicKey_RSA2.crt";
//        config.alipayRootCertPath = "D:\\Github\\paperless-recruit\\paperless-recruit-doc\\账号密码\\支付宝1\\alipayRootCert.crt";
//
//        //可设置异步通知接收服务地址（可选）
////        config.notifyUrl = "<-- 请填写您的支付类接口异步通知接收服务地址，例如：https://www.test.com/callback -->";
//
//        //可设置AES密钥，调用AES加解密相关接口时需要（可选）
//        config.encryptKey = "XuGXgaGE1yHCRiofdu6Wmw==";
//
//        return config;
//    }
//
//}

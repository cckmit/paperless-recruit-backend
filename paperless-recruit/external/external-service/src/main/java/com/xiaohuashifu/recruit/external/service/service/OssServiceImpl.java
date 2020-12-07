package com.xiaohuashifu.recruit.external.service.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;

import java.io.ByteArrayInputStream;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2020/12/7 17:09
 */
public class OssServiceImpl {
    public static void main(String[] args) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-guangzhou.aliyuncs.com";
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI4GHw6M37rPWbiJWRJtcU";
        String accessKeySecret = "Oi9UNj1M6OrYSVmSGezu0MJeD8x7Ze";
        String bucketName = "scau-recruit";
// <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        String objectName = "test &'\"<>/te.txt";

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。
        String content = "";
        PutObjectResult putObjectResult = ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content.getBytes()));
        System.out.println(putObjectResult.getETag());
        System.out.println(putObjectResult.getVersionId());
        System.out.println(putObjectResult.getClientCRC());
        System.out.println(putObjectResult.getRequestId());
        System.out.println(putObjectResult.getResponse());
        System.out.println(putObjectResult.getServerCRC());

// 关闭OSSClient。
        ossClient.shutdown();
    }
}

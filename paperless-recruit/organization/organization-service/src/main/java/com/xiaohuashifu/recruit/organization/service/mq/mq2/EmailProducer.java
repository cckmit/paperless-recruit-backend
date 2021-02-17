package com.xiaohuashifu.recruit.organization.service.mq.mq2;

import com.alibaba.fastjson.JSON;
import com.xiaohuashifu.recruit.external.api.request.CreateAndSendEmailAuthCodeRequest;
import com.xiaohuashifu.recruit.external.api.request.CreateAndSendSmsAuthCodeRequest;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2021/2/15 00:16
 */
public class EmailProducer {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("EmailProducer");
        producer.setNamesrvAddr("49.233.30.197:9876");
        producer.start();
        for (int i = 0; i < 2; i++) {
            CreateAndSendEmailAuthCodeRequest req = CreateAndSendEmailAuthCodeRequest.builder()
                    .email("827032783@qq.com").subject("email-update").title("邮箱绑定").expirationTime(5).build();

            Message message = new Message("Email",
                    "CreateAndSendEmailAuthCode",
                    JSON.toJSONBytes(req));
            SendResult sendResult = producer.send(message);
            System.out.println(sendResult);
        }
        producer.shutdown();
    }

}

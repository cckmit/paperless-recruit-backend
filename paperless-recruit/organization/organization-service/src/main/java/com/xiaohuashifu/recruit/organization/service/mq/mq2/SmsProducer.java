package com.xiaohuashifu.recruit.organization.service.mq.mq2;

import com.alibaba.fastjson.JSON;
import com.xiaohuashifu.recruit.external.api.request.CreateAndSendSmsAuthCodeRequest;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2021/2/15 00:16
 */
public class SmsProducer {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("SmsProducer");
        producer.setNamesrvAddr("49.233.30.197:9876");
        producer.start();
        for (int i = 0; i < 2; i++) {
            CreateAndSendSmsAuthCodeRequest request = CreateAndSendSmsAuthCodeRequest.builder()
                    .phone("15992321303").expirationTime(5).subject("register" + i).build();

            Message message = new Message("Sms",
                    "CreateAndSendSmsAuthCode",
                    JSON.toJSONBytes(request));
            SendResult sendResult = producer.send(message);
            System.out.println(sendResult);
        }
        producer.shutdown();
    }

}

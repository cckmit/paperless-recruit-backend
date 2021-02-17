package com.xiaohuashifu.recruit.organization.service.mq.mq2;

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
public class SQLProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("lmq_group");
        producer.setNamesrvAddr("49.233.30.197:9876");
        producer.start();

        for (int i = 0; i < 10; i++) {
            Message message = new Message("TopicTest",
                    "TagD",
                    ("Hello RocketMQ" + i).getBytes());
            message.putUserProperty("a", String.valueOf(i));
            SendResult sendResult = producer.send(message);
            System.out.println(sendResult);
        }
        producer.shutdown();
    }

}

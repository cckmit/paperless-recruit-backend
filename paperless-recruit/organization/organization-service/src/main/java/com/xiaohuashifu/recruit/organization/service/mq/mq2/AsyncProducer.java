package com.xiaohuashifu.recruit.organization.service.mq.mq2;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2021/2/15 00:16
 */
public class AsyncProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("lmq_group");
        producer.setNamesrvAddr("49.233.30.197:9876");
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);
        CountDownLatch2 countDownLatch = new CountDownLatch2(100);
        for (int i = 0; i < 100; i++) {
            final int index = i;
            Message message = new Message("TopicTest", "TagB",
                    "OrderID188",
                    ("Hello RocketMQ" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(index + sendResult.getMsgId());
                    countDownLatch.countDown();
                }

                @Override
                public void onException(Throwable e) {
                    System.out.println(index);
                    e.printStackTrace();
                }
            });
        }
        countDownLatch.await(5, TimeUnit.SECONDS);
        producer.shutdown();
    }

}

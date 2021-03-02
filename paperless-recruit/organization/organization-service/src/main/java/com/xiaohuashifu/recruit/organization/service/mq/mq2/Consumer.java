package com.xiaohuashifu.recruit.organization.service.mq.mq2;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.impl.consumer.ConsumeMessageConcurrentlyService;
import org.apache.rocketmq.client.impl.consumer.RebalancePushImpl;
import org.apache.rocketmq.client.impl.factory.MQClientInstance;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.namesrv.NamesrvConfig;
import org.apache.rocketmq.common.namesrv.NamesrvUtil;
import org.apache.rocketmq.common.protocol.header.namesrv.DeleteTopicInNamesrvRequestHeader;
import org.apache.rocketmq.common.utils.NameServerAddressUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import sun.misc.Unsafe;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2021/2/15 00:35
 */
public class Consumer {


    public static void main(String[] args) throws MQClientException {
        StringBuilder stringBuilder = new StringBuilder("");
        System.out.println(stringBuilder.toString());

//
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("lmq_group");
//        consumer.setNamesrvAddr("49.233.30.197:9876");
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
//        consumer.subscribe("TopicTest", "*");
//        consumer.registerMessageListener(new MessageListenerOrderly() {
//            Random random = new Random();
//            @Override
//            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
//                context.setAutoCommit(true);
//                for (MessageExt msg : msgs) {
//                    System.out.println("consumeThread=" + Thread.currentThread().getName() + "queueId=" + msg.getQueueId() + ", content:" + new String(msg.getBody()));
//                }
//                try {
//                    //模拟业务逻辑处理中...
//                    TimeUnit.SECONDS.sleep(random.nextInt(10));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return ConsumeOrderlyStatus.SUCCESS;
//            }
//        });
//        consumer.start();
    }

}


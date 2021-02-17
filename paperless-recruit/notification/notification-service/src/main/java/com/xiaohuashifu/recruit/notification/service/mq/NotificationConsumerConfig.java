package com.xiaohuashifu.recruit.notification.service.mq;

import com.alibaba.fastjson.JSON;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.notification.api.request.SendSystemNotificationRequest;
import com.xiaohuashifu.recruit.notification.api.service.SystemNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述：通知消费者配置
 *
 * @author xhsf
 * @create 2021/2/16 02:17
 */
@Configuration
@Slf4j
public class NotificationConsumerConfig {

    @Reference
    private SystemNotificationService systemNotificationService;

    @Value("${rocketmq.name-server}")
    private String nameServer;

    @Value("${rocketmq.topics.notification}")
    private String topic;

    @Value("${rocketmq.consumer.group.send-system-notification}")
    private String sendSystemNotificationConsumerGroup;

    @Value("${rocketmq.tags.send-system-notification}")
    private String sendSystemNotificationTag;

    /**
     * 发送系统消息消费者
     *
     * @return DefaultMQPushConsumer
     * @throws MQClientException .
     */
    @Bean
    public DefaultMQPushConsumer sendSystemNotificationConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(sendSystemNotificationConsumerGroup);
        consumer.setNamesrvAddr(nameServer);
        consumer.subscribe(topic, sendSystemNotificationTag);
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            for (MessageExt msg : msgs) {
                String msgString = new String(msg.getBody());
                SendSystemNotificationRequest request = JSON.parseObject(msgString, SendSystemNotificationRequest.class);
                try {
                    systemNotificationService.sendSystemNotification(request);
                } catch (NotFoundServiceException e) {
                    log.warn("Send system notification, " + e.getMessage() + "." + msgString, e);
                }
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
        return consumer;
    }

    /**
     * 关闭时：摧毁 Bean sendSystemNotificationConsumer
     *
     * @return DisposableBean
     */
    @Bean
    public DisposableBean destroy() {
        return ()-> sendSystemNotificationConsumer().shutdown();
    }
    
}

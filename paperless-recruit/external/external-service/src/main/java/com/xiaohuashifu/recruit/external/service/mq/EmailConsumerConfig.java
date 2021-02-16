package com.xiaohuashifu.recruit.external.service.mq;

import com.alibaba.fastjson.JSON;
import com.xiaohuashifu.recruit.common.exception.UnknownServiceException;
import com.xiaohuashifu.recruit.external.api.request.CreateAndSendEmailAuthCodeRequest;
import com.xiaohuashifu.recruit.external.api.request.SendSimpleEmailRequest;
import com.xiaohuashifu.recruit.external.api.request.SendTemplateEmailRequest;
import com.xiaohuashifu.recruit.external.api.service.EmailService;
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
 * 描述：Email 消费者配置
 *
 * @author xhsf
 * @create 2021/2/16 02:17
 */
@Configuration
@Slf4j
public class EmailConsumerConfig {

    @Reference
    private EmailService emailService;

    @Value("${rocketmq.name-server}")
    private String nameServer;

    @Value("${rocketmq.topics.email}")
    private String topic;

    @Value("${rocketmq.consumer.group.create-and-send-email-auth-code}")
    private String createAndSendEmailAuthCodeConsumerGroup;

    @Value("${rocketmq.consumer.group.send-simple-email}")
    private String sendSimpleEmailConsumerGroup;

    @Value("${rocketmq.consumer.group.send-template-email}")
    private String sendTemplateEmailConsumerGroup;

    @Value("${rocketmq.tags.create-and-send-email-auth-code}")
    private String createAndSendEmailAuthCodeTag;

    @Value("${rocketmq.tags.send-simple-email}")
    private String sendSimpleEmailTag;

    @Value("${rocketmq.tags.send-template-email}")
    private String sendTemplateEmailTag;

    /**
     * 创建并发送邮件验证码消费者，消费失败直接丢弃信息
     *
     * @return DefaultMQPushConsumer
     * @throws MQClientException .
     */
    @Bean
    public DefaultMQPushConsumer createAndSendEmailAuthCodeConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(createAndSendEmailAuthCodeConsumerGroup);
        consumer.setNamesrvAddr(nameServer);
        consumer.subscribe(topic, createAndSendEmailAuthCodeTag);
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            for (MessageExt msg : msgs) {
                String msgString = new String(msg.getBody());
                CreateAndSendEmailAuthCodeRequest request = JSON.parseObject(msgString,
                        CreateAndSendEmailAuthCodeRequest.class);
                try {
                    emailService.createAndSendEmailAuthCode(request);
                } catch (UnknownServiceException e) {
                    log.warn("Create and send EmailAuthCode failed." + msgString, e);
                }
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
        return consumer;
    }

    /**
     * 发送简单邮件消费者
     *
     * @return DefaultMQPushConsumer
     * @throws MQClientException .
     */
    @Bean
    public DefaultMQPushConsumer sendSimpleEmailConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(sendSimpleEmailConsumerGroup);
        consumer.setNamesrvAddr(nameServer);
        consumer.subscribe(topic, sendSimpleEmailTag);
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            for (MessageExt msg : msgs) {
                String msgString = new String(msg.getBody());
                SendSimpleEmailRequest request = JSON.parseObject(msgString, SendSimpleEmailRequest.class);
                emailService.sendSimpleEmail(request);
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
        return consumer;
    }

    /**
     * 发送模板邮件消费者
     *
     * @return DefaultMQPushConsumer
     * @throws MQClientException .
     */
    @Bean
    public DefaultMQPushConsumer sendTemplateEmailConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(sendTemplateEmailConsumerGroup);
        consumer.setNamesrvAddr(nameServer);
        consumer.subscribe(topic, sendTemplateEmailTag);
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            for (MessageExt msg : msgs) {
                String msgString = new String(msg.getBody());
                SendTemplateEmailRequest request = JSON.parseObject(msgString, SendTemplateEmailRequest.class);
                emailService.sendTemplateEmail(request);
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
        return consumer;
    }

    /**
     * 关闭时：摧毁 Bean createAndSendEmailAuthCodeConsumer，sendSimpleEmailConsumer
     *
     * @return DisposableBean
     */
    @Bean
    public DisposableBean destroy() {
        return ()-> {
            createAndSendEmailAuthCodeConsumer().shutdown();
            sendSimpleEmailConsumer().shutdown();
            sendTemplateEmailConsumer().shutdown();
        };
    }
    
}

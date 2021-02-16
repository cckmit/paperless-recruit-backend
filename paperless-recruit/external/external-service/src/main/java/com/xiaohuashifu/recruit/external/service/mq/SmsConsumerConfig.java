package com.xiaohuashifu.recruit.external.service.mq;

import com.alibaba.fastjson.JSON;
import com.xiaohuashifu.recruit.common.exception.LimitControlServiceException;
import com.xiaohuashifu.recruit.common.exception.UnknownServiceException;
import com.xiaohuashifu.recruit.external.api.request.CreateAndSendSmsAuthCodeRequest;
import com.xiaohuashifu.recruit.external.api.service.SmsService;
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
 * 描述：Sms 消费者配置
 *
 * @author xhsf
 * @create 2021/2/16 02:17
 */
@Configuration
@Slf4j
public class SmsConsumerConfig {

    @Reference
    private SmsService smsService;

    @Value("${rocketmq.consumer.group}")
    private String consumerGroup;

    @Value("${rocketmq.name-server}")
    private String nameServer;

    @Value("${rocketmq.topics.sms}")
    private String topic;

    @Value("${rocketmq.tags.create-and-send-sms-auth-code}")
    private String createAndSendSmsAuthCodeTag;

    /**
     * 创建并发送短信验证码消费者，消费失败直接丢弃信息
     *
     * 频率：1分钟内短信发送条数不超过：1
     * 1小时内短信发送条数不超过：5
     * 1个自然日内短信发送条数不超过：10
     *
     * @return DefaultMQPushConsumer
     * @throws MQClientException .
     */
    @Bean
    public DefaultMQPushConsumer createAndSendSmsAuthCodeConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(nameServer);
        consumer.subscribe(topic, createAndSendSmsAuthCodeTag);
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            for (MessageExt msg : msgs) {
                String msgString = new String(msg.getBody());
                CreateAndSendSmsAuthCodeRequest request = JSON.parseObject(msgString,
                        CreateAndSendSmsAuthCodeRequest.class);
                try {
                    smsService.createAndSendSmsAuthCode(request);
                } catch (UnknownServiceException | LimitControlServiceException e) {
                    log.warn("Create and send SmsAuthCode failed." + msgString, e);
                }
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
        return consumer;
    }

    /**
     * 关闭时：摧毁 Bean createAndSendSmsAuthCodeConsumer
     *
     * @return DisposableBean
     */
    @Bean
    public DisposableBean destroy() {
        return ()-> createAndSendSmsAuthCodeConsumer().shutdown();
    }
    
}

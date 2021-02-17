package com.xiaohuashifu.recruit.organization.service.mq;

import com.alibaba.fastjson.JSON;
import com.xiaohuashifu.recruit.common.exception.MqServiceException;
import com.xiaohuashifu.recruit.notification.api.request.SendSystemNotificationRequest;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 描述：通知模板
 *
 * @author xhsf
 * @create 2021/2/17 15:06
 */
@Component
public class NotificationTemplate {

    private final DefaultMQProducer defaultMQProducer;

    @Value("${rocketmq.topics.notification}")
    private String notificationTopic;

    @Value("${rocketmq.tags.send-system-notification}")
    private String sendSystemNotificationTag;

    public NotificationTemplate(DefaultMQProducer defaultMQProducer) {
        this.defaultMQProducer = defaultMQProducer;
    }

    /**
     * 发送系统通知，同步发送
     *
     * @param request SendSystemNotificationRequest
     * @throws MqServiceException 发送出错时抛出
     */
    public void sendSystemNotification(SendSystemNotificationRequest request) throws MqServiceException {
        Message message = new Message(notificationTopic, sendSystemNotificationTag, JSON.toJSONBytes(request));
        try {
            defaultMQProducer.send(message);
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            throw new MqServiceException("Send system notification error." + request, e);
        }
    }

}

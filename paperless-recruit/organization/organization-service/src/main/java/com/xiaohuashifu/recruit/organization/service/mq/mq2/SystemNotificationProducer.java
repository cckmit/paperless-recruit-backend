package com.xiaohuashifu.recruit.organization.service.mq.mq2;

import com.alibaba.fastjson.JSON;
import com.xiaohuashifu.recruit.external.api.request.CreateAndSendSmsAuthCodeRequest;
import com.xiaohuashifu.recruit.notification.api.constant.SystemNotificationTypeEnum;
import com.xiaohuashifu.recruit.notification.api.request.SendSystemNotificationRequest;
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
public class SystemNotificationProducer {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("NotificationProducer");
        producer.setNamesrvAddr("49.233.30.197:9876");
        producer.start();
        for (int i = 0; i < 2; i++) {
            SendSystemNotificationRequest request = SendSystemNotificationRequest.builder()
                    .userId(1L)
                    .notificationTitle("校科联成员邀请")
                    .notificationType(SystemNotificationTypeEnum.ORGANIZATION_INVITATION)
                    .notificationContent("校科联邀请您成为他们的成员").build();

            Message message = new Message("Notification",
                    "SendSystemNotification",
                    JSON.toJSONBytes(request));
            SendResult sendResult = producer.send(message);
            System.out.println(sendResult);
        }
        producer.shutdown();
    }

}

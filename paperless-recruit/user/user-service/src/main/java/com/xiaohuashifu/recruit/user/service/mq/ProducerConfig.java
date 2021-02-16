package com.xiaohuashifu.recruit.user.service.mq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述：生产者配置
 *
 * @author xhsf
 * @create 2021/2/17 00:12
 */
@Configuration
public class ProducerConfig {

    @Value("${rocketmq.producer.group}")
    private String producerGroup;

    @Value("${rocketmq.name-server}")
    private String nameServer;

    /**
     * 生产者
     *
     * @return DefaultMQProducer
     * @throws MQClientException .
     */
    @Bean
    public DefaultMQProducer defaultMQProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(nameServer);
        producer.start();
        return producer;
    }

    /**
     * 摧毁 DefaultMQProducer
     *
     * @return DisposableBean
     */
    @Bean
    public DisposableBean destroy() {
        return ()-> defaultMQProducer().shutdown();
    }

}

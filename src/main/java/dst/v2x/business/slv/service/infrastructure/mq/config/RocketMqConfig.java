package dst.v2x.business.slv.service.infrastructure.mq.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketMqConfig {

    @Value("${rocketmq.name-server}")
    private String nameServer;

    @Value("${rocketmq.producer.group}")
    private String producerGroup;

    @Value("${rocketmq.producer.send-message-timeout}")
    private Integer sendMsgTimeout;

    @Value("${rocketmq.producer.max-message-size}")
    private Integer maxMessageSize;

    @Value("${rocketmq.producer.retry-times-when-send-failed}")
    private Integer retryTimesWhenSendFailed;

    @Value("${rocketmq.producer.retry-times-when-send-async-failed}")
    private Integer retryTimesWhenSendAsyncFailed;

    // 使用默认值为空字符串，避免没有配置时报错
    @Value("${rocketmq.producer.access-key:}")
    private String accessKey;

    @Value("${rocketmq.producer.secret-key:}")
    private String secretKey;

    @Bean
    public RocketMQTemplate rocketMqTemplateDefault() {
        RocketMQTemplate rocketMqTemplate = new RocketMQTemplate();
        rocketMqTemplate.setProducer(defaultMqProducer());
        return rocketMqTemplate;
    }

    @Bean
    public DefaultMQProducer defaultMqProducer() {
        DefaultMQProducer producer;
        if (StringUtils.isEmpty(accessKey) || StringUtils.isEmpty(secretKey)) {
            producer = new DefaultMQProducer(this.producerGroup);
        } else {
            producer = new DefaultMQProducer(producerGroup, new AclClientRPCHook(new SessionCredentials(accessKey, secretKey)));
        }
        producer.setNamesrvAddr(this.nameServer);
        producer.setSendMsgTimeout(this.sendMsgTimeout);
        producer.setMaxMessageSize(this.maxMessageSize);
        producer.setRetryTimesWhenSendFailed(this.retryTimesWhenSendFailed);
        producer.setRetryTimesWhenSendAsyncFailed(this.retryTimesWhenSendAsyncFailed);
        return producer;
    }
}

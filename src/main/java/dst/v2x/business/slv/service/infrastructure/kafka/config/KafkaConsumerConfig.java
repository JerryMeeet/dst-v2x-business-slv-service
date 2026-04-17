package dst.v2x.business.slv.service.infrastructure.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteBufferDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * KafKa消费配置类
 *
 * @author 张朝
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    /**
     * 消费者超过该值没有返回心跳，服务端判断消费者处于非存活状态，服务端将消费者从Group移除并触发Rebalance，默认30s。
     */
    @Value("${spring.kafka.consumer.properties.session.timeout.ms:30000}")
    private int sessionTimeoutMs;

    /**
     * 消费请求的超时时间
     */
    @Value("${spring.kafka.consumer.properties.session.timeout.ms:18000}")
    private int requestTimeoutMs;

    /**
     * 注意该值不要改得太大，如果poll太多数据，而不能在下次poll之前消费完，则会触发一次负载均衡，产生卡顿
     */
    @Value("${spring.kafka.consumer.max-poll-records}")
    private int maxPollRecords;


    /**
     * 是否开启手动提交
     */
    @Value("${spring.kafka.consumer.enable-auto-commit}")
    private Boolean enableAutoCommit;

    /**
     * 指定了消费者在开始读取没有已提交偏移量或已提交偏移量超出范围的分区时的行为
     * earliest：如果对于某个分区没有已提交的偏移量，消费者将从该分区的最早可用偏移量开始读取。
     * latest：如果对于某个分区没有已提交的偏移量，消费者将从该分区的最新可用偏移量开始读取。
     * none：如果对于某个分区没有已提交的偏移量，消费者将抛出异常。这个设置要求在开始消费之前始终提交偏移量。
     */
    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    /**
     * 设置每个消费者从每个分区一次拉取的最大数据量
     */
    @Value("${spring.kafka.consumer.max-partition-fetch-bytes}")
    private int maxPartitionFetchBytes;


    /**
     * 设置每次请求从服务器返回的最大数据量
     */
    @Value("${spring.kafka.consumer.fetch-max-bytes}")
    private int fetchMaxBytes;


    /**
     * 设置每次请求从服务器返回的最大数据量
     */
    @Value("${spring.kafka.consumer.connection-max-idle-ms}")
    private int connectionMaxIdleMs;


    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;


    @Value("${spring.kafka.listener.concurrency}")
    private int concurrency;

    // 使用默认值为空字符串，避免没有配置时报错
    @Value("${spring.kafka.username:}")
    private String username;


    @Value("${spring.kafka.password:}")
    private String password;


    /**
     * kafka监听器容器工厂
     */
    @Bean
    ConcurrentKafkaListenerContainerFactory<String, ByteBuffer> kafkaListenerContainerByteBufferFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ByteBuffer> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        // 设置确认模式
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);

        //并发消费者初始化值
        factory.setConcurrency(concurrency);

        return factory;
    }

    /**
     * 消费工厂
     */
    public ConsumerFactory<String, ByteBuffer> consumerFactory() {
        Map<String, Object> props = consumerConfigs();
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }


    /**
     * 消费者配置
     */
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>(16);

        //Kafka服务地址
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        // 如果配置了用户名和密码，则添加SASL认证
        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            // SASL认证机制
            props.put("security.protocol", "SASL_PLAINTEXT");
            props.put("sasl.mechanism", "PLAIN");

            // SASL认证配置
            String saslJaasConfig = String.format("org.apache.kafka.common.security.plain.PlainLoginModule required username=\"%s\" password=\"%s\";", username, password);
            props.put("sasl.jaas.config", saslJaasConfig);
        }

        //两次Poll之间的最大允许间隔。消费者超过该值没有返回心跳，服务端判断消费者处于非存活状态，服务端将消费者从Group移除并触发Rebalance，默认30s。
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutMs);

        //消费请求的超时时间
        props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeoutMs);

        //自动提交最好禁用，开启手动提交
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);

        //指定消费者连接的最大空闲时间，超过该时间，服务端会关闭该连接
        props.put(ConsumerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG, connectionMaxIdleMs);

        //指定了消费者在开始读取没有已提交偏移量或已提交偏移量超出范围的分区时的行为
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);

        //设置每个消费者从每个分区一次拉取的最大数据量，走公网访问时，该参数会有较大影响。
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, maxPartitionFetchBytes);

        //设置每次请求从服务器返回的最大数据量，该值不要改得太大，如果设的太大，而不能在下次请求之前消费完，则会触发一次负载均衡，产生卡顿。
        props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, fetchMaxBytes);

        //每次poll的最大数量。注意该值不要改得太大，如果poll太多数据，而不能在下次poll之前消费完，则会触发一次负载均衡，产生卡顿。
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);

        //因为如果两次poll的事件超出了30秒的时间间隔，Kafka会认为其消费能力过弱，将其提出消费组。将分区分配给其他消费者
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 30 * 1000);

        //消费者发送心跳的时间间隔
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 1000);

        return props;
    }
}

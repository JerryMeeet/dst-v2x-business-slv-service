#Nacos配置
# redis
spring.data.redis.host=172.16.8.165
spring.data.redis.port=6379
spring.data.redis.database=1
spring.data.redis.password=c12nkqkosVNptsjj

#rocketmq NameServer地址
rocketmq.name-server=172.16.8.171:9876
#rocketmq 生产者配置
rocketmq.producer.send-message-timeout=30000
rocketmq.producer.group=dst-v2x-business-slv-service-producer
rocketmq.producer.max-message-size=102400
rocketmq.producer.retry-times-when-send-failed=3
rocketmq.producer.retry-times-when-send-async-failed=3

# mybatis配置
mybatis-plus.configuration.auto-mapping-unknown-column-behavior=WARNING
mybatis-plus.configuration.map-underscore-to-camel-case=true
mybatis-plus.mapper-locations=classpath:mapper/**/*.xml
mybatis-plus.type-aliases-package=dst.v2x.business.slv.service.infrastructure.biz.**.entity

#配置数据源
spring.datasource.mysql.url=jdbc:mysql://172.16.8.164:30308/dst_v2x_db?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&serverTimezone=Asia/Shanghai
spring.datasource.mysql.username=root
spring.datasource.mysql.password=U9Jf13bIao
spring.datasource.mysql.driver-class-name=com.mysql.cj.jdbc.Driver

spring.datasource.doris.url=jdbc:mysql://172.16.8.227:9030/dst_v2x_db_dev?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&serverTimezone=Asia/Shanghai
spring.datasource.doris.username=root
spring.datasource.doris.password=3hDD31HLadamcY
spring.datasource.doris.driver-class-name=com.mysql.cj.jdbc.Driver



# Kafka配置
# 是否开启手动提交
spring.kafka.consumer.enable-auto-commit=false
# 提交offset延时
spring.kafka.consumer.auto-commit-interval=2000
# 单次拉取消息的最大字节数
spring.kafka.consumer.max-partition-fetch-bytes=1048576
spring.kafka.consumer.fetch-max-bytes=5242880
spring.kafka.consumer.connection-max-idle-ms=540000
spring.kafka.consumer.raw.topic=dst_iot_slv_raw_topic-test
spring.kafka.consumer.alarm.topic=dst_iot_slv_alarm_topic-test
spring.kafka.consumer.can.topic=dst_iot_slv_can_topic-test
spring.kafka.consumer.consumer-group=dst-v2x-business-slv-service-dev
# 当kafka中没有初始offset或offset超出范围时将自动重置offset
# earliest:重置为分区中最小的offset;
# latest:重置为分区中最新的offset(消费分区中新产生的数据);
# none:只要有一个分区不存在已提交的offset,就抛出异常;
spring.kafka.consumer.auto-offset-reset=latest
# 单次拉取消息的最大条数
spring.kafka.consumer.max-poll-records=100
# 并发消费者初始化值
spring.kafka.listener.concurrency=20
# 消费会话超时时间（超过这个时间 consumer 没有发送心跳，就会触发 rebalance 操作）
spring.kafka.consumer.properties.session.timeout.ms=30000
# 消费请求的超时时间
spring.kafka.consumer.properties.request.timeout.ms=18000
#consumer listener topics 不存在时，启动项目就会报错
spring.kafka.listener.missing-topics-fatal=false
spring.kafka.bootstrap-servers=172.16.8.228:9092,172.16.8.229:9092,172.16.8.230:9092


#白犀牛
baixiniu.remote.url=http://dev.api.white-rhino.auto
baixiniu.remote.appKey=bxn_dst
baixiniu.remote.appSecret=j35z2ywfwki0cbrx


#九识无人车
#test环境
#zelos.auth.url=http://auth-uat.zelostech.com.cn
#正式环境
zelos.auth.url=https://auth.zelostech.com.cn
#应用id
zelos.appId=oafce80986433468a8fb51e38cfde984e
#应用密钥
zelos.appKey=Mzk3NmExZmItNDYxNS00ZjJmLTg3MDQtYTM0ZTA4NzZhOGY4
#接口调用域名
#zelos.remote.url=http://gateway-uat.zelostech.com.cn
zelos.remote.url=http://gateway.zelostech.com.cn


#新石器无人车
#测试环境
neolix.remote.tokenUrl=https://scapi.test.neolix.net
#应用id
#neolix.clientId=apitest
neolix.clientId=dst_api
#应用密钥
#neolix.clientSecret=apitestneolix
neolix.clientSecret=4fa67ad0-5266-4d1b-9c4f-1adae2addbb7
#接口调用域名
neolix.remote.url=https://scapi.test.neolix.net/openapi-server
#企业编码
neolix.enterpriseCode=DST0021
#固定值 按需分配
neolix.business=DST
#RTMP 视频对接token
neolix.rtmp.token=6fvuz2wmfpvtary2
#RTMP 视频对接url
neolix.remote.rtmp.url=https://space-shanghai-left.neolix.net:57006


#新石器无人车
#生产环境
#neolix.remote.tokenUrl=https://scapi.neolix.net
#应用id
#neolix.clientId=dstapi
#应用密钥
#neolix.clientSecret=203bbfd1-93da-4380-8b44-ff96f9447e69
#接口调用域名
#neolix.remote.url=https://scapi.neolix.net/openapi-server
#企业编码
#neolix.enterpriseCode=DST0021

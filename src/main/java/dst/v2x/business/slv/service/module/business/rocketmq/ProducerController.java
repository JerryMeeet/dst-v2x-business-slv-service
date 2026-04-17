package dst.v2x.business.slv.service.module.business.rocketmq;

import com.dst.steed.vds.common.domain.response.Response;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 影子服务 -测试RocketMQ消息发送
 */
@RestController
public class ProducerController {

    @Autowired
    private RocketMQTemplate rocketMqTemplateDefault;


    /**
     * 发送简单消息
     */
    @GetMapping("/send")
    public Response<?> sendMessage() {
        String message = "Hello RocketMQ!";
        // 发送消息到指定 topic
        rocketMqTemplateDefault.convertAndSend("Acho_Topic", message);

        return Response.succeed("Message sent: " + message);
    }


    /**
     * 发送带有标签的消息
     */
    @GetMapping("/sendTag")
    public Response<?> sendMessageWithTag() {
        String message = "Hello RocketMQ with Tag!";
        // 发送带有标签的消息到指定 topic
        rocketMqTemplateDefault.convertAndSend("test-topic:tagA", message);
        return Response.succeed("Message with tag sent: " + message);
    }
}

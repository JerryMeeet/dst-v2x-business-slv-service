package dst.v2x.business.slv.service.infrastructure.acl.iotda.config;//package dst.v2x.business.slv.service.infrastructure.kafka.config;

import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.core.http.HttpConfig;
import com.huaweicloud.sdk.core.region.Region;
import com.huaweicloud.sdk.iotda.v5.IoTDAClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Iotda配置
 */
@Configuration
@EnableKafka
public class IotdaConfig {

    // REGION_ID：如果是上海一，请填写"cn-east-3"；如果是北京四，请填写"cn-north-4";如果是华南广州，请填写"cn-south-1"
    @Value("${iotda.regionId:cn-south-1}")
    private String regionId;

    // ENDPOINT：请在控制台的"总览"界面的"平台接入地址"中查看“应用侧”的https接入地址。
    @Value("${iotda.endpoint:54328d67ee.st1.iotda-app.cn-south-1.myhuaweicloud.com}")
    private String endpoint;

    @Value("${iotda.ak:HPUAM6KHCJ6WVYMFALHG}")
    private String ak;

    @Value("${iotda.sk:OrbgAKqkeTuceZlyd9CJ7KRzv7UQRSRILeYeVlb7}")
    private String sk;

    @Value("${iotda.projectId:672d3d4d02f34fd4ab3cfe6d3162c863}")
    private String projectId;

    @Bean
    public IoTDAClient ioTDAClient() {
        // 创建认证
        ICredential auth = new BasicCredentials()
                .withAk(ak)
                .withSk(sk)
                // 标准版/企业版需要使用衍生算法，基础版请删除配置"withDerivedPredicate"
                .withDerivedPredicate(BasicCredentials.DEFAULT_DERIVED_PREDICATE)
                .withProjectId(projectId)
                ;

        // 创建IoTDAClient实例并初始化
        IoTDAClient client = IoTDAClient.newBuilder()
                .withCredential(auth)
                // 标准版/企业版：需自行创建Region对象，基础版：请使用IoTDARegion的region对象，如"withRegion(IoTDARegion.CN_NORTH_4)"
                .withRegion(new Region(regionId, endpoint))
//                 配置是否忽略SSL证书校验， 默认不忽略
                .withHttpConfig(new HttpConfig().withIgnoreSSLVerification(true))
                .build();
        return client;
    }
}

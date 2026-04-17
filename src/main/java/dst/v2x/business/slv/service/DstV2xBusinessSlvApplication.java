package dst.v2x.business.slv.service;

import com.dst.steed.vds.common.util.DstSpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.util.StopWatch;

import java.text.DecimalFormat;

import static dst.v2x.business.slv.service.DstV2xBusinessSlvApplication.PACKAGE_NAME;

/**
 * 项目启动类
 * @author 张朝
 */
@Slf4j
@ServletComponentScan
@SpringBootApplication(scanBasePackages = {PACKAGE_NAME})
@EnableFeignClients(basePackages = PACKAGE_NAME + ".infrastructure.acl")
@EnableDiscoveryClient
//@MapperScan(basePackages = {PACKAGE_NAME + ".infrastructure.biz.**.mapper", PACKAGE_NAME + ".infrastructure.doris.mapper"})
public class DstV2xBusinessSlvApplication {

    public static final String PACKAGE_NAME = "dst.v2x.business.slv.service";

    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        SpringApplication.run(DstV2xBusinessSlvApplication.class, args);
        stopWatch.stop();

        log.info("【服务：" + DstSpringUtil.getAppName() +
                "；环境：" + DstSpringUtil.getActiveProfile() +
                "】启动成功1，耗时：" +
                new DecimalFormat("#.##").format(stopWatch.getTotalTimeSeconds()) + " 秒。");
    }

}

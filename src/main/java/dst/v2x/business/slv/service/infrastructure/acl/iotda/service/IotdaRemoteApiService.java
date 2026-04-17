package dst.v2x.business.slv.service.infrastructure.acl.iotda.service;

import com.huaweicloud.sdk.core.exception.ClientRequestException;
import com.huaweicloud.sdk.iotda.v5.IoTDAClient;
import com.huaweicloud.sdk.iotda.v5.model.AddDevice;
import com.huaweicloud.sdk.iotda.v5.model.AddDeviceRequest;
import com.huaweicloud.sdk.iotda.v5.model.AddDeviceResponse;
import com.huaweicloud.sdk.iotda.v5.model.AuthInfo;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * iotda远程接口服务
 */
@Slf4j
@Service
public class IotdaRemoteApiService {
    @Autowired
    private IoTDAClient iotdaClient;

    // 产品ID
    @Value("${iotda.productId:685ba41ba72147363d8b9930}")
    private String productId;

    // 认证类型
    @Value("${iotda.authType:SECRET}")
    private String authType;

    //设备已存在编码
    private static final String DEVICE_EXIST_CODE = "IOTDA.014031";

    /**
     * 新增设备
     * @param vehicleNo 车辆编号
     * @param vehicleCompanyEnum 车辆公司枚举
     * @return 设备ID
     */
    public String addDevice(String vehicleNo, VehicleCompanyEnum vehicleCompanyEnum) {
        try {
            AddDevice body = new AddDevice();
            AuthInfo authInfobody = new AuthInfo();
            authInfobody.withAuthType(authType)
                    .withSecret(vehicleCompanyEnum.getName() + "_" + vehicleNo)
                    .withSecureAccess(true);
            body.withAuthInfo(authInfobody);
            body.withProductId(productId);
            body.withNodeId(vehicleNo);

            AddDeviceRequest request = new AddDeviceRequest();
            request.withBody(body);

            log.info("调用iotda新增设备接口，车辆编码【{}】，企业【{}】，入参【{}】",
                    vehicleNo,
                    vehicleCompanyEnum.getDesc(),
                    request);
            AddDeviceResponse response = iotdaClient.addDevice(request);
            log.info("调用iotda新增设备接口，车辆编码【{}】，企业【{}】，返回结果【{}】",
                    vehicleNo,
                    vehicleCompanyEnum.getDesc(),
                    response);
            return response.getDeviceId();
        } catch (ClientRequestException e) {
            //设备已存在，直接拼接设备ID返回，设备ID格式为：产品ID_vehicleNo
            if(DEVICE_EXIST_CODE.equals(e.getErrorCode())){
                log.warn("调用iotda新增设备接口，车辆编码【{}】，企业【{}】，返回结果【{}】",
                        vehicleNo,
                        vehicleCompanyEnum.getDesc(),
                        "设备已存在");
                return productId + "_" + vehicleNo;
            }else {
                log.error("调用iotda新增设备接口异常", e);
            }
        } catch (Exception e) {
            log.error("调用iotda新增设备接口异常", e);
        }
        return null;
    }
}

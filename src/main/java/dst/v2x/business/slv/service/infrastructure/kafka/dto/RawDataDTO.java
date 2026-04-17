package dst.v2x.business.slv.service.infrastructure.kafka.dto;

import lombok.Data;

/**
 * 上报实时数据
 */
@Data
public class RawDataDTO {
    /**
     * 基础信息
     */
    private BasicInfoDTO basicInfo;

    /**
     * 运营信息
     */
    private OperationInfoDTO operationInfo;

    /**
     * CAN信息
     */
    private CanInfoDTO canInfo;
}

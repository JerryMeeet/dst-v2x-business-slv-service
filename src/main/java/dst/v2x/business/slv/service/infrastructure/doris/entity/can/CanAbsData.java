package dst.v2x.business.slv.service.infrastructure.doris.entity.can;

import lombok.Data;

/**
 * CAN数据-ABS
 */
@Data
public class CanAbsData {
    /**
     * 左前轮速
     */
    private Double whlSpdFL;

    /**
     * 右前轮速
     */
    private Double whlSpdFR;

    /**
     * 左后轮速
     */
    private Double whlSpdRL;

    /**
     * 右后轮速
     */
    private Double whlSpdRR;
}
package dst.v2x.business.slv.service.infrastructure.doris.vo.can;

import dst.v2x.business.slv.service.common.annotation.StatusDesc;
import dst.v2x.business.slv.service.common.enums.can.CanVcuDriveModeEnum;
import dst.v2x.business.slv.service.common.enums.can.CanVcuGearEnum;
import lombok.Data;

/**
 * Can数据-VCU整车控制系统
 */
@Data
public class CanVcuDataVO {
    /**
     * 车速
     */
    private Double vehicleSpeed;

    /**
     * 累计里程
     */
    private Double vehicleOdo;

    /**
     * 车辆挡位状态
     * 0：P 挡
     * 1：D 挡
     * 2：N 挡
     * 3：R 挡
     */
    private Integer vehicleGear;
    @StatusDesc(fieldName = "vehicleGear", enumClass = CanVcuGearEnum.class)
    private String vehicleGearName;

    /**
     * 油门踏板开度(%)
     */
    private Double acceleratorPedalStatus;

    /**
     * 制动踏板开度(%)
     */
    private Double brakePedalStatus;

    /**
     * 低压蓄电池电压值
     */
    private Double bmsKeyOn;

    /**
     * 车辆运行模式
     * 0表示遥控模式，优先级最二
     * 1表示AD模式，优先级第三
     * 2表示空闲模式，优先级最低（遥控器关机，或者遥控器开机按着A 键并且连续10s遥控器没有动作）
     * 3表示故障模式，优先级最高，禁止运行AD模式，只能由空闲模式进入。
     */
    private Integer driveModeState;
    @StatusDesc(fieldName = "driveModeState", enumClass = CanVcuDriveModeEnum.class)
    private String driveModeStateName;

}

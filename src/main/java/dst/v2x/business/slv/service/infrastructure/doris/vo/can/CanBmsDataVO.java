package dst.v2x.business.slv.service.infrastructure.doris.vo.can;

import dst.v2x.business.slv.service.common.annotation.StatusDesc;
import dst.v2x.business.slv.service.common.enums.base.OpenCloseEnum;
import dst.v2x.business.slv.service.common.enums.can.CanBmsChargeStsEnum;
import dst.v2x.business.slv.service.common.enums.can.CanBmsReqHvDownEnum;
import dst.v2x.business.slv.service.common.enums.can.CanBmsSelfChkEnum;
import lombok.Data;

/**
 * Can数据-bms
 */
@Data
public class CanBmsDataVO {
    /**
     * 动力电池最大允许能量回馈功率
     */
    private Double bmsChgPwr;

    /**
     * 自检状态
     * 0: 自检中
     * 1: 自检成功
     * 2: 自检失败
     */
    private Integer bmsSelfChkSts;
    @StatusDesc(fieldName = "bmsSelfChkSts", enumClass = CanBmsSelfChkEnum.class)
    private String bmsSelfChkStsName;

    /**
     * 可充电储能装
     */
    private Double bmsHVBatVol;

    /**
     * 可充电储能装置总电流
     */
    private Double bmsHVBatCrnt;

    /**
     * SOH（电池健康度%）
     */
    private Double bmsHVDisplaySOH;

    /**
     * 电池单体最高温度值
     */
    private Double bmsHVBatHighestTem;

    /**
     * 电池单体最低温度值
     */
    private Double bmsHVBatLowestTem;

    /**
     * 0x00: 非充电状态(比如休眠放电中放电故障等)
     * 0x01: 充电中
     * 0x02 : 加热中
     * 0x03 : 边充电边加热中
     * 0x04: 充电完成(在充电完成时，检测到充电故障发充电完成)
     * 0x05: 充电故障
     */
    private Integer chargeSts;
    @StatusDesc(fieldName = "chargeSts", enumClass = CanBmsChargeStsEnum.class)
    private String chargeStsName;

    /**
     * 紧急下高压电请求
     * 0x00: 无请求
     * 0x01: 请求高压下电
     */
    private Integer bmsReqHvDown;
    @StatusDesc(fieldName = "bmsReqHvDown", enumClass = CanBmsReqHvDownEnum.class)
    private String bmsReqHvDownName;

    /**
     * 加热继电器状态（加热继电器状态0x00 : open，0x01: close ）
     */
    private Integer heatRelaySts;
    @StatusDesc(fieldName = "heatRelaySts", enumClass = OpenCloseEnum.class)
    private String heatRelayStsName;
}

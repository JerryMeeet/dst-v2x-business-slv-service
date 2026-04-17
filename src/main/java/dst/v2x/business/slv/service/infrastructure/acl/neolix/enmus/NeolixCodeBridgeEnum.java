package dst.v2x.business.slv.service.infrastructure.acl.neolix.enmus;

import dst.v2x.business.slv.service.common.enums.raw.RawAlarmTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 新石器告警枚举
 *
 * @author 江民来
 * @date 2025/6/18 16:26
 * @return null
 */
@AllArgsConstructor
@Getter
public enum NeolixCodeBridgeEnum {
    type_1("MCU", "编码器故障P180700", RawAlarmTypeEnum.type_33),
    type_2("can0", "can0", RawAlarmTypeEnum.type_34),
    type_3("EPB", "EPB指示右电机驻车超时", RawAlarmTypeEnum.type_35),
    type_4("TC397", "DCDC未工作", RawAlarmTypeEnum.type_36),
    type_5("radar_point_cloud", "欠压关断", RawAlarmTypeEnum.type_37),
    type_6("TC397", "BCU指示SOC差值过大", RawAlarmTypeEnum.type_38),
    type_7("EPS", "EPS转角传感器信号故障", RawAlarmTypeEnum.type_39),
    type_8("EPB", "EPB指示左电机驻车超时", RawAlarmTypeEnum.type_40),
    type_9("cage_control", "过流关断", RawAlarmTypeEnum.type_41),
    type_10("network_latency", "网络延迟", RawAlarmTypeEnum.type_42),
    type_11("BCU", "BMU_A单体电压低(非科易电池)", RawAlarmTypeEnum.type_43),
    type_12("lan_communication", "局域网通信", RawAlarmTypeEnum.type_44),
    type_13("EHB", "液压力传感器故障", RawAlarmTypeEnum.type_45),
    type_14("TC397", "轮速异常", RawAlarmTypeEnum.type_46),
    type_15("positioning", "定位异常", RawAlarmTypeEnum.type_47);

    private final String thirdFaultCode; // 第三方枚举值
    private final String thirdFaultDesc; // 第三方枚举名称
    private final RawAlarmTypeEnum mappedEnum; // 我们自己的告警类型

    private static Map<String, RawAlarmTypeEnum> codeBridgeMap;

    static {
        codeBridgeMap = new HashMap<>();
        for (NeolixCodeBridgeEnum bridge : values()) {
            codeBridgeMap.put(bridge.thirdFaultCode, bridge.mappedEnum);
        }
    }

    /**
     * 根据code获取对应的枚举
     *
     * @param faultCode
     * @return RawAlarmTypeEnum
     * @author 江民来
     * @date 2025/6/18 16:26
     */
    public static RawAlarmTypeEnum getAlarmType(String faultCode) {
        RawAlarmTypeEnum rawAlarmTypeEnum = codeBridgeMap.get(faultCode);
        return rawAlarmTypeEnum == null ? RawAlarmTypeEnum.type_0 : rawAlarmTypeEnum;
    }
}

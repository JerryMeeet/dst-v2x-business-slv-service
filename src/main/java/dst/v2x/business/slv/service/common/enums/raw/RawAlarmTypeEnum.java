package dst.v2x.business.slv.service.common.enums.raw;

import dst.v2x.business.slv.service.common.enums.BaseEnum;
import dst.v2x.business.slv.service.infrastructure.biz.alarm.vo.AlarmEnumVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 车辆告警枚举类型枚举
 * 接收多家车辆告警枚举信息，最后归纳成我们自己的枚举类型
 *
 * @author 江民来
 * @date 2025/6/12 16:27
 */
@AllArgsConstructor
@Getter
public enum RawAlarmTypeEnum implements BaseEnum {
    type_0(0, "其他"),
    type_1(1, "卡死不走"),
    type_2(2, "行驶中开门"),
    type_3(3, "胎压低"),
    type_4(4, "爆胎"),
    type_5(5, "故障"),
    type_6(6, "碰撞停车"),
    type_7(7, "pnc失败"),
    type_8(8, "车辆任务中离线"),
    type_9(9, "电池温度高"),
    type_10(10, "升级模块异常"),
    type_11(11, "制冷机温度异常"),
    type_12(12, "制冷机被关闭"),
    type_13(13, "疑似卡死"),
    type_14(14, "制冷机状态异常"),
    type_15(15, "传感器异常"),
    type_16(16, "天气模式异常"),
    type_17(17, "电量低报警"),
    type_18(18, "残压报警"),
    type_19(19, "标定异常"),
    type_20(20, "停车异常"),
    type_21(21, "底盘二级故障"),
    type_22(22, "潜在碰撞可能"),
    type_23(23, "路口卡死"),
    type_24(24, "转弯卡死"),
    type_25(25, "误排队卡死"),
    type_26(26, "铁路卡顿"),
    type_27(27, "信号灯结果互斥"),
    type_28(28, "长时间远离导航"),
    type_29(29, "自由规划脱困失败"),
    type_30(30, "拍杆卡死"),
    type_31(31, "会车卡死"),
    type_32(32, "施工区域卡死"),
    // 下面是新石器的枚举
    type_33(33, "编码器故障P180700"),
    type_34(34, "can0"),
    type_35(35, "EPB指示右电机驻车超时"),
    type_36(36, "DCDC未工作"),
    type_37(37, "欠压关断"),
    type_38(38, "BCU指示SOC差值过大"),
    type_39(39, "EPS转角传感器信号故障"),
    type_40(40, "EPB指示左电机驻车超时"),
    type_41(41, "过流关断"),
    type_42(42, "网络延迟"),
    type_43(43, "BMU_A单体电压低(非科易电池)"),
    type_44(44, "局域网通信"),
    type_45(45, "液压力传感器故障"),
    type_46(46, "轮速异常"),
    type_47(47, "定位");


    private final Integer code;
    private final String desc;
    // 定义一个map
    private static LinkedHashMap<Integer, String> rawAlarmMap = new LinkedHashMap<>();

    // 将本枚举类中所有的数据写入map
    static {
        for (RawAlarmTypeEnum alarm : values()) {
            rawAlarmMap.put(alarm.code, alarm.desc);
        }
    }

    /**
     * 根据Code获取Name
     *
     * @param code
     * @author 江民来
     * @date 2025/6/12 17:19
     */
    public static String getNameByCode(Integer code) {
        return rawAlarmMap.getOrDefault(code, RawAlarmTypeEnum.type_0.getDesc());
    }

    /**
     * 根据name获取Code
     *
     * @param name
     * @author 江民来
     * @date 2025/6/12 17:19
     */
    public static Integer getCodeByName(String name) {
        for (RawAlarmTypeEnum type : RawAlarmTypeEnum.values()) {
            if (StringUtils.equals(name, type.desc)) {
                return type.code;
            }
        }
        return RawAlarmTypeEnum.type_0.code; // 如果没有找到对应的返回0
    }

    /**
     * 获取枚举类中所有的数据
     *
     * @return List<AlarmEnumVo>
     * @author 江民来
     * @date 2025/6/18 17:28
     */
    public static List<AlarmEnumVo> toEnumVOList() {
        return rawAlarmMap.entrySet().stream()
                .map(entry -> new AlarmEnumVo(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}

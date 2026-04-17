package dst.v2x.business.slv.service.common.enums.can;

import dst.v2x.business.slv.service.common.enums.BaseEnum;
import lombok.Getter;

/**
 * Can数据-VCU整车控制系统-车辆运行模式
 */
@Getter
public enum CanVcuDriveModeEnum implements BaseEnum {
    MODE_0(0, "遥控模式"),
    MODE_1(1, "AD模式"),
    MODE_2(2, "空闲模式"),
    MODE_3(3, "故障模式");

    private final Integer code;
    private final String desc;

    CanVcuDriveModeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(Integer code) {
        for (CanVcuDriveModeEnum sortValueEnum : values()) {
            if (sortValueEnum.getCode().equals(code)) {
                return sortValueEnum.getDesc();
            }
        }
        return null;
    }


}

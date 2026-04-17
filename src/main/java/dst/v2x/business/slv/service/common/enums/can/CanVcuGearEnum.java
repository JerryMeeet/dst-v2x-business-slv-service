package dst.v2x.business.slv.service.common.enums.can;

import dst.v2x.business.slv.service.common.enums.BaseEnum;
import lombok.Getter;

/**
 * Can数据-VCU整车控制系统-挡位
 */
@Getter
public enum CanVcuGearEnum implements BaseEnum {
    GEAR_0(0, "P挡"),
    GEAR_1(1, "D挡"),
    GEAR_2(2, "N挡"),
    GEAR_3(3, "R挡");

    private final Integer code;
    private final String desc;

    CanVcuGearEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(Integer code) {
        for (CanVcuGearEnum sortValueEnum : values()) {
            if (sortValueEnum.getCode().equals(code)) {
                return sortValueEnum.getDesc();
            }
        }
        return null;
    }


}

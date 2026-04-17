package dst.v2x.business.slv.service.common.enums.raw;

import dst.v2x.business.slv.service.common.enums.BaseEnum;
import lombok.Getter;

/**
 * 辆故障状态枚举
 */
@Getter
public enum VehicleErrorStatusEnum implements BaseEnum {

    ERROR1(1, "正常"),
    ERROR2(2, "故障"),
    ERROR3(3, "自我修复中");

    private final Integer code;
    private final String desc;

    VehicleErrorStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(Integer code) {
        for (VehicleErrorStatusEnum sortValueEnum : values()) {
            if (sortValueEnum.getCode().equals(code)) {
                return sortValueEnum.getDesc();
            }
        }
        return null;
    }


}

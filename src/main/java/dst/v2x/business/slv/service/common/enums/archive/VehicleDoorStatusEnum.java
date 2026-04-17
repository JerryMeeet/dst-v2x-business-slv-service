package dst.v2x.business.slv.service.common.enums.archive;

import dst.v2x.business.slv.service.common.enums.BaseEnum;
import lombok.Getter;

/**
 * 驾驶状态：1 自动驾驶，2 遥控，3 停车中
 */
@Getter
public enum VehicleDoorStatusEnum implements BaseEnum {

    DOOR_0(0, "门未全部关闭"),
    DOOR_1(1, "门已全部关闭");

    private final Integer code;
    private final String desc;

    VehicleDoorStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(Integer code) {
        for (VehicleDoorStatusEnum sortValueEnum : values()) {
            if (sortValueEnum.getCode().equals(code)) {
                return sortValueEnum.getDesc();
            }
        }
        return null;
    }


}

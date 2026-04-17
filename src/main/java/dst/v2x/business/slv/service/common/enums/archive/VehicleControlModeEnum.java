package dst.v2x.business.slv.service.common.enums.archive;

import dst.v2x.business.slv.service.common.enums.BaseEnum;
import lombok.Getter;

/**
 * 驾驶状态：1 自动驾驶，2 遥控，3 停车中
 */
@Getter
public enum VehicleControlModeEnum implements BaseEnum {

    MODE1(1, "自动驾驶"),
    MODE2(2, "遥控驾驶"),
    MODE3(3, "停车中"),
    MODE4(4, "远程驾驶");

    private final Integer code;
    private final String desc;

    VehicleControlModeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(Integer code) {
        for (VehicleControlModeEnum sortValueEnum : values()) {
            if (sortValueEnum.getCode().equals(code)) {
                return sortValueEnum.getDesc();
            }
        }
        return null;
    }


}

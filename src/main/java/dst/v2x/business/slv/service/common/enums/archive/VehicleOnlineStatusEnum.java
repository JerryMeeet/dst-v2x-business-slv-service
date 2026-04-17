package dst.v2x.business.slv.service.common.enums.archive;

import dst.v2x.business.slv.service.common.enums.BaseEnum;
import lombok.Getter;

/**
 * 开关机状态：0 关机，1 开机
 *
 * @author: zy
 * @date: 2024/11/16 11:19
 */
@Getter
public enum VehicleOnlineStatusEnum implements BaseEnum {

    OFF_LINE(0, "离线"),
    ON_LINE(1, "在线");

    private final Integer code;
    private final String desc;

    VehicleOnlineStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(Integer code) {
        for (VehicleOnlineStatusEnum sortValueEnum : values()) {
            if (sortValueEnum.getCode().equals(code)) {
                return sortValueEnum.getDesc();
            }
        }
        return null;
    }


}

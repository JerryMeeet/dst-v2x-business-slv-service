package dst.v2x.business.slv.service.common.enums.archive;

import dst.v2x.business.slv.service.common.enums.BaseEnum;
import lombok.Getter;

/**
 * 是否充电中：1充电中 0未充电-1网络异常不同步
 *
 * @author: zy
 * @date: 2025/3/13 11:21
 */
@Getter
public enum VehicleChargeStatusEnum implements BaseEnum {

    CHARGING(1, "充电中"),

    NOT_CHARGING(0, "未充电"),

    NETWORK_EXCEPTION(-1, "网络异常不同步");

    private final Integer code;
    private final String desc;

    VehicleChargeStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(Integer code) {
        for (VehicleChargeStatusEnum sortValueEnum : values()) {
            if (sortValueEnum.getCode().intValue() == code.intValue()) {
                return sortValueEnum.getDesc();
            }
        }
        return null;
    }
}

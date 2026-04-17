package dst.v2x.business.slv.service.common.enums.archive;

import dst.v2x.business.slv.service.common.enums.BaseEnum;
import lombok.Getter;

/**
 * 充电枪状态：0未插 I正在插着-1网络异常不同步
 *
 * @author: zy
 * @date: 2025/3/13 11:26
 */
@Getter
public enum VehicelChargeGunStatusEnum implements BaseEnum {


    NOT_CHARGING(0, "未插"),

    CHARGING(1, "正在插着"),

    NETWORK_EXCEPTION(-1, "网络异常不同步");

    private final Integer code;
    private final String desc;

    VehicelChargeGunStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(Integer code) {
        for (VehicelChargeGunStatusEnum value : VehicelChargeGunStatusEnum.values()) {
            if (value.getCode().intValue() == code.intValue()) {
                return value.getDesc();
            }
        }
        return null;
    }
}

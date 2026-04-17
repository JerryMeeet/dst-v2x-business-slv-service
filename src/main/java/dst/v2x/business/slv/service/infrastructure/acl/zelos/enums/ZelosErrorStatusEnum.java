package dst.v2x.business.slv.service.infrastructure.acl.zelos.enums;

import dst.v2x.business.slv.service.common.enums.BaseEnum;
import lombok.Getter;

/**
 * 辆故障状态枚举-九识
 */
@Getter
public enum ZelosErrorStatusEnum implements BaseEnum {
    ERROR1(1, "正常"),
    ERROR2(2, "故障"),
    ERROR3(3, "⾃我修复中");

    private final Integer code;
    private final String desc;

    ZelosErrorStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(Integer code) {
        for (ZelosErrorStatusEnum sortValueEnum : values()) {
            if (sortValueEnum.getCode().equals(code)) {
                return sortValueEnum.getDesc();
            }
        }
        return null;
    }


}

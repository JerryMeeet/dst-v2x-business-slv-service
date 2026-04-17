package dst.v2x.business.slv.service.common.enums.can;

import dst.v2x.business.slv.service.common.enums.BaseEnum;
import lombok.Getter;

/**
 * Can数据-bms-充电状态
 */
@Getter
public enum CanBmsChargeStsEnum implements BaseEnum {
    STS_0(0, "非充电状态"),
    STS_1(1, "充电中"),
    STS_2(2, "加热中"),
    STS_3(3, "边充电边加热中"),
    STS_4(4, "充电完成"),
    STS_5(5, "充电故障")
    ;

    private final Integer code;
    private final String desc;

    CanBmsChargeStsEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(Integer code) {
        for (CanBmsChargeStsEnum sortValueEnum : values()) {
            if (sortValueEnum.getCode().equals(code)) {
                return sortValueEnum.getDesc();
            }
        }
        return null;
    }


}

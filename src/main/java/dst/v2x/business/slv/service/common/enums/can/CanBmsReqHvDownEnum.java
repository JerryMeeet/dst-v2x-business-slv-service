package dst.v2x.business.slv.service.common.enums.can;

import dst.v2x.business.slv.service.common.enums.BaseEnum;
import lombok.Getter;

/**
 * Can数据-bms-紧急下高压电请求
 */
@Getter
public enum CanBmsReqHvDownEnum implements BaseEnum {
    STS_0(0, "无请求"),
    STS_1(1, "请求高压下电")
    ;

    private final Integer code;
    private final String desc;

    CanBmsReqHvDownEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(Integer code) {
        for (CanBmsReqHvDownEnum sortValueEnum : values()) {
            if (sortValueEnum.getCode().equals(code)) {
                return sortValueEnum.getDesc();
            }
        }
        return null;
    }


}

package dst.v2x.business.slv.service.common.enums.base;

import dst.v2x.business.slv.service.common.enums.BaseEnum;
import lombok.Getter;

/**
 * Open Close状态
 */
@Getter
public enum OpenCloseEnum implements BaseEnum {
    OPEN_0(0, "open"),
    CLOSE_1(1, "close");

    private final Integer code;
    private final String desc;

    OpenCloseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(Integer code) {
        for (OpenCloseEnum sortValueEnum : values()) {
            if (sortValueEnum.getCode().equals(code)) {
                return sortValueEnum.getDesc();
            }
        }
        return null;
    }


}

package dst.v2x.business.slv.service.common.enums.can;

import dst.v2x.business.slv.service.common.enums.BaseEnum;
import lombok.Getter;

/**
 * Can数据-bms-自检状态
 */
@Getter
public enum CanBmsSelfChkEnum implements BaseEnum {
    CHK_0(0, "自检中"),
    CHK_1(1, "自检成功"),
    CHK_2(2, "自检失败");

    private final Integer code;
    private final String desc;

    CanBmsSelfChkEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(Integer code) {
        for (CanBmsSelfChkEnum sortValueEnum : values()) {
            if (sortValueEnum.getCode().equals(code)) {
                return sortValueEnum.getDesc();
            }
        }
        return null;
    }


}

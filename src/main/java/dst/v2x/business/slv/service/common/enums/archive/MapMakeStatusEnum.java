package dst.v2x.business.slv.service.common.enums.archive;

import dst.v2x.business.slv.service.common.enums.BaseEnum;
import lombok.Getter;

/**
 * 路线状态枚举
 * @date 2025/8/18 16:27
 */
@Getter
public enum MapMakeStatusEnum implements BaseEnum {
    STATUS_1(1, "已完成交付"),
    STATUS_2(2, "新建"),
    STATUS_3(3, "测试中"),
    STATUS_4(4, "采图中");

    private final Integer code;
    private final String desc;

    MapMakeStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(Integer belongCompany) {
        for (MapMakeStatusEnum sortValueEnum : values()) {
            if (sortValueEnum.getCode() == belongCompany) {
                return sortValueEnum.getDesc();
            }
        }
        return null;
    }
}

package dst.v2x.business.slv.service.common.enums.archive;

import dst.v2x.business.slv.service.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum VehicleCompanyEnum implements BaseEnum {

    VEHICLE_COMPANY_1(1, "WHITERHINO", "白犀牛无人车"),
    VEHICLE_COMPANY_2(2, "ZELOS", "九识无人车"),
    VEHICLE_COMPANY_3(3, "NEOLIX", "新石器无人车");

    private final int code; // 枚举值
    private final String name; //枚举值对应的公司名
    private final String desc; // 枚举值对应的描述

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDescByCode(Integer belongCompany) {
        for (VehicleCompanyEnum sortValueEnum : values()) {
            if (sortValueEnum.getCode() == belongCompany) {
                return sortValueEnum.getDesc();
            }
        }
        return null;
    }
}

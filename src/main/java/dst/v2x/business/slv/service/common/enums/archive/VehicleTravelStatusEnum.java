package dst.v2x.business.slv.service.common.enums.archive;

import dst.v2x.business.slv.service.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 行驶状态：1 空闲中，2 配送中
 *
 * @author: zy
 * @date: 2024/11/16 11:19
 */
@Getter
@AllArgsConstructor
@ToString
public enum VehicleTravelStatusEnum implements BaseEnum {

    IDLE_STATUS(1, "空闲中"),

    TRAVEL_STATUS(2, "配送中");;

    private final Integer code;
    private final String desc;

    public static String getDescByCode(Integer code) {
        for (VehicleTravelStatusEnum sortValueEnum : values()) {
            if (sortValueEnum.getCode().equals(code)) {
                return sortValueEnum.getDesc();
            }
        }
        return null;
    }


}

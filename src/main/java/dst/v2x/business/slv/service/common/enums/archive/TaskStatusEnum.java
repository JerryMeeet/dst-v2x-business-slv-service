package dst.v2x.business.slv.service.common.enums.archive;

import dst.v2x.business.slv.service.common.enums.BaseEnum;
import lombok.Getter;

/**
 * 任务状态枚举
 * 接收多家枚举信息，最后归纳成我们自己的枚举类型
 *
 * @author 江民来
 * @date 2025/6/12 16:27
 */
@Getter
public enum TaskStatusEnum implements BaseEnum {
    STATUS_1(1, "已创建"),
    STATUS_2(2, "执行中"),
    STATUS_3(3, "已完成"),
    STATUS_4(4, "已取消");

    private final Integer code;
    private final String desc;

    TaskStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(Integer belongCompany) {
        for (TaskStatusEnum sortValueEnum : values()) {
            if (sortValueEnum.getCode() == belongCompany) {
                return sortValueEnum.getDesc();
            }
        }
        return null;
    }
}

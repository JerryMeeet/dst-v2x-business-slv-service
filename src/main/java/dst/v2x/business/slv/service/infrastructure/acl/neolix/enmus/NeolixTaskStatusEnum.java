package dst.v2x.business.slv.service.infrastructure.acl.neolix.enmus;

import dst.v2x.business.slv.service.common.enums.archive.TaskStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 新石器任务状态枚举
 *
 * @author 江民来
 * @date 2025/6/18 16:26
 * @return null
 */
@AllArgsConstructor
@Getter
public enum NeolixTaskStatusEnum {
    STATUS_1(1, "执行中", TaskStatusEnum.STATUS_2),
    STATUS_2(2, "待执行", TaskStatusEnum.STATUS_1),
    STATUS_3(3, "已完成", TaskStatusEnum.STATUS_3),
    STATUS_4(4, "已取消", TaskStatusEnum.STATUS_4);

    private final Integer code; // 第三方枚举值
    private final String name; // 第三方枚举名称
    private final TaskStatusEnum mappedEnum; // 我们自己的枚举

    private static Map<Integer, TaskStatusEnum> codeBridgeMap;

    static {
        codeBridgeMap = new HashMap<>();
        for (NeolixTaskStatusEnum bridge : values()) {
            codeBridgeMap.put(bridge.code, bridge.mappedEnum);
        }
    }

    /**
     * 根据三方code获取对应的dst枚举code
     */
    public static Integer getTaskStatusCode(Integer code) {
        TaskStatusEnum taskStatusEnum = codeBridgeMap.get(code);
        return taskStatusEnum==null?null:taskStatusEnum.getCode();
    }
}

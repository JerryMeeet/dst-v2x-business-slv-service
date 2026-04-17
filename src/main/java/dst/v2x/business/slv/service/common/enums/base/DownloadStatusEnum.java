package dst.v2x.business.slv.service.common.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 下载状态枚举
 */
@Getter
@AllArgsConstructor
public enum DownloadStatusEnum {
    STATUS_0(0, "待处理"),
    STATUS_1(1, "处理中"),
    STATUS_2(2, "处理完成"),
    STATUS_3(3, "处理失败"),
    STATUS_4(4, "未获取到数据");

    private Integer value;

    private String name;

    public static String getNameByValue(Integer value) {
        String name = "";
        if (value != null) {
            if (value.equals(STATUS_0.getValue())) {
                name = STATUS_0.getName();
            } else if (value.equals(STATUS_1.getValue())) {
                name = STATUS_1.getName();
            } else if (value.equals(STATUS_2.getValue())) {
                name = STATUS_2.getName();
            } else if (value.equals(STATUS_3.getValue())) {
                name = STATUS_3.getName();
            } else if (value.equals(STATUS_4.getValue())) {
                name = STATUS_4.getName();
            }
        }
        return name;
    }
}

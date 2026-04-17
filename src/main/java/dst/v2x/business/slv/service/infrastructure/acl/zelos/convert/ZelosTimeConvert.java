package dst.v2x.business.slv.service.infrastructure.acl.zelos.convert;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 九识转换类
 */
@Getter
public class ZelosTimeConvert {
    /**
     * 九识时间转换，同一字段存在两种结构，需要做兼容性转换
     */
    public static LocalDateTime convertZelosTime(String time) {
        if (StringUtils.isBlank(time)) {
            return null;
        }
        if (time.contains(".")) {
            return LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));
        }
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}

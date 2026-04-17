package dst.v2x.business.slv.service.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/**
 * @Description: 时间处理工具
 * @Author: 张朝
 * @create: 2025-04-11-16:45
 */
public class DataUtils {


    /**
     * 转换 LocalDate
     *
     * @param date 日期
     * @return 日期
     */
    public static Date getDate(LocalDate date) {
        if (Objects.isNull(date)) {
            return null;
        }
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }


    /**
     * 转换 LocalDateTime 为时间戳（毫秒数）
     *
     * @param date 要转换的 LocalDateTime 对象
     * @return 对应的时间戳（毫秒数），如果输入为 null 则返回 null
     */
    public static Long getDate(LocalDateTime date) {
        if (Objects.isNull(date)) {
            return null;
        }
        return date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 转换 LocalDateTime 为时间戳（毫秒数）
     *
     * @param date 要转换的 LocalDateTime 对象
     * @return 对应的时间戳（毫秒数），如果输入为 null 则返回 null
     */
    public static Date getDateFromLocalDateTime(LocalDateTime date) {
        if (Objects.isNull(date)) {
            return null;
        }
        return Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
    }
}

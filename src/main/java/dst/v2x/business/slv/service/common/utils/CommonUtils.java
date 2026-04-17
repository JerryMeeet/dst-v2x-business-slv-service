package dst.v2x.business.slv.service.common.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

/**
 * 手写一些工具类 放到这个里面
 *
 * @author 江民来
 * @date 2025年03月12日 17:00
 */
public class CommonUtils {

    // 车架号的正则表达式
    private static final Pattern VIN_PATTERN = Pattern.compile("^[A-Za-z0-9]{17}$");

    /**
     * 将秒级时间戳转换为LocalDateTime。
     *
     * @param timestamp 秒级时间戳
     * @return 转换后的LocalDateTime对象
     */
    public static LocalDateTime convertSecondsToLocalDateTime(long timestamp) {
        // 将秒级时间戳转换为毫秒级时间戳
        Instant instant = Instant.ofEpochSecond(timestamp);
        // 使用系统默认时区将Instant转换为LocalDateTime
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    /**
     * 把String类型的时间转换成LocalDateTime
     *
     * @param dateTimeStr yyyy-MM-dd HH:mm:ss
     * @return java.time.LocalDateTime
     * @author 江民来
     * @date 2025/5/30 18:00
     */
    public static LocalDateTime parseToLocalDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.isEmpty()) {
            return null;
        }
        // 截掉毫秒部分（只保留前 19 位：yyyy-MM-dd HH:mm:ss）
        if (dateTimeStr.length() > 19) {
            dateTimeStr = dateTimeStr.substring(0, 19);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN);
        try {
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (DateTimeParseException e) {
            // 可根据需要选择抛出异常或记录日志后返回 null
            System.err.println("时间格式错误：" + dateTimeStr);
            return null;
        }
    }

    /**
     * 将 LocalDateTime 转换为 yyyy-MM-dd HH:mm:ss 格式的字符串
     */
    public static String formatToDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN);
        return dateTime.format(formatter);
    }

    /**
     * 把LocalDateTime类型的时间转换成yyMMddHHmmss
     *
     * @param dateTime 时间
     * @return Long
     * @author 江民来
     * @date 2025/5/30 18:03
     */
    public static String formatToShortTimestamp(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        return dateTime.format(formatter);
    }

    /**
     * 将long类型的毫秒级时间戳转换成正常时间格式
     *
     * @return java.lang.String
     * @author 江民来
     * @date 2025/5/22 16:16
     */
    public static String convertTime(Long milliTimestamp) {
        LocalDateTime dateTime = Instant.ofEpochMilli(milliTimestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        // yyyy-MM-dd HH:mm:ss
        String formatted = dateTime.format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN));
        return formatted;
    }

    /**
     * 根据2个时间，计算时间差，得到中间间隔的秒数
     *
     * @param startTime 开始时间 格式(2025-02-03 22:33:23)
     * @param endTime   结束时间 格式(2025-02-03 22:33:53)
     * @return Long
     * @author 江民来
     * @date 2025/3/12 18:11
     */
    public static Long betweenSecondDate(String startTime, String endTime) {
        DateTime parse1 = DateUtil.parse(startTime);
        DateTime parse2 = DateUtil.parse(endTime);
        return DateUtil.between(parse1, parse2, DateUnit.SECOND);
    }

    /**
     * 校验车架号是否合格
     *
     * @param carVin
     * @return boolean 合格-true,不合格-false
     * @author 江民来
     * @date 2025/5/26 14:07
     */
    public static boolean isValidCarVin(String carVin) {
        return carVin != null && VIN_PATTERN.matcher(carVin).matches();
    }
}

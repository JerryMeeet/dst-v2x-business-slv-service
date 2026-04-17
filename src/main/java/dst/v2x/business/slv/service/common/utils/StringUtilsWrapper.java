
package dst.v2x.business.slv.service.common.utils;

import com.google.common.base.CharMatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * StringUtils工具类
 *
 * @author liwenjie
 * @date 2021-01-05
 * @since 1.0
 */
@Slf4j
public final class StringUtilsWrapper extends StringUtils {

    /***
     * 去除左右空格（包含中文全角、半角、回车等）
     */
    public static final CharSequence SEQUENCE = "\r\n\t \u00A0　‭";

    private StringUtilsWrapper() {

    }

    /**
     * 去左右空格
     *
     * @param value
     * @return
     */
    public static String trim(String value) {
        return value == null ? null : CharMatcher.anyOf(SEQUENCE).trimFrom(value);
    }

    public static String valueOf(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }

    /**
     * 删除换行符\n \t
     *
     * @param value
     * @return
     */
    public static String replaceLine(String value) {
        if (!hasText(value)) {
            return value;
        }
        return value.replace("\n", "").replace("\t", "");
    }

    /**
     * 判断是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (!hasText(str)) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; ++i) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否json类型
     *
     * @param contentType
     * @return
     */
    public static boolean isJsonContentType(String contentType) {
        if (StringUtils.isEmpty(contentType)) {
            return false;
        }
        return contentType.contains(MediaType.APPLICATION_JSON_VALUE);
    }

    /**
     * 判断两对象是否相等
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(Object a, Object b) {
        return Objects.equals(a, b);
    }

    public static String getStringByList(List<String> list) {
        if (CollectionUtilsWrapper.isEmpty(list)) {
            return "";
        }

        StringBuffer str = new StringBuffer();
        list.forEach(item -> {
            str.append(StringUtilsWrapper.trim(item));
            str.append(",");
        });
        str.deleteCharAt(str.length() - 1);

        return str.toString();
    }

    public static Map<String, Object> paramsTrim(Map<String, Object> oldParams) {
        Map<String, Object> newParams = new HashMap<>(oldParams.size());
        if (CollectionUtils.isEmpty(oldParams)) {
            return newParams;
        }
        for (Map.Entry<String, Object> entry : oldParams.entrySet()) {
            String key = trim(entry.getKey());
            Object value = entry.getValue();

            if (value instanceof String) {
                value = CharMatcher.anyOf(SEQUENCE).trimFrom((CharSequence) value);
                newParams.put(key, value);
            } else if (value instanceof Map) {
                newParams.put(key, paramsTrim((Map<String, Object>) value));
            } else if (value instanceof List) {
                newParams.put(key, listParams((List<Object>) value));
            } else {
                newParams.put(key, value);
            }
        }
        return newParams;
    }

    /**
     * 集合参数
     *
     * @param listParams
     * @return
     */
    private static List<Object> listParams(List<Object> listParams) {
        List<Object> list = new ArrayList<>(listParams.size());
        for (Object o : listParams) {
            if (o instanceof List) {
                return listParams((List<Object>) o);
            } else if (o instanceof Map) {
                list.add(paramsTrim((Map<String, Object>) o));
            } else if (o instanceof String) {
                list.add(trim((String) o));
            } else {
                list.add(o);
            }
        }
        return list;
    }

}
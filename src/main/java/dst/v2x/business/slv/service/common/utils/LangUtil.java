package dst.v2x.business.slv.service.common.utils;

public class LangUtil {
    /**
     * 将字符串转换为Double类型
     * @param value
     * @return
     */
    public static Double parseDouble(String value) {
        if(value == null){
            return null;
        }
        return Double.parseDouble(value);
    }

    /**
     * 将字符串转换为Integer类型
     * @param value
     * @return
     */
    public static Integer parseInteger(String value) {
        if(value == null){
            return null;
        }
        return Integer.valueOf(value);
    }
}

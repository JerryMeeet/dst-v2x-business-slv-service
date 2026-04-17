package dst.v2x.business.slv.service.infrastructure.biz.dict;


import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;

import java.lang.reflect.Field;

/**
 * 默认字段翻译器
 * <p>
 * 拿到注解的值, 然后去找字典缓存进行翻译.
 */
public class DefaultDictFieldTranslator implements DictFieldTranslator {


    @Override
    public String searchDictValue(Object dictObject, Field sourcefield, Object fieldValue, DictMapping annotationValue) {

        // 数据校验
        int byteValidateLength = annotationValue.byteValidateLength();
        if (byteValidateLength > 0 && fieldValue instanceof String var) {
            if (StrUtil.isNullOrUndefined(var)) {
                return null;
            }
            long fieldValueLong = NumberUtil.parseLong(var);
            if (byteValidateLength == 1 && NumberUtil.equals(fieldValueLong, 254L)) {
                return Dict.BYTE_ERROR_VALUE;
            }
            if (byteValidateLength == 1 && NumberUtil.equals(fieldValueLong, 255L)) {
                return Dict.BYTE_INVALID_VALUE;
            }
            if (byteValidateLength == 2 && NumberUtil.equals(fieldValueLong, 4094L)) {
                return Dict.BYTE_ERROR_VALUE;
            }
            if (byteValidateLength == 2 && NumberUtil.equals(fieldValueLong, 4095L)) {
                return Dict.BYTE_INVALID_VALUE;
            }
            if (byteValidateLength == 3 && NumberUtil.equals(fieldValueLong, 65534L)) {
                return Dict.BYTE_ERROR_VALUE;
            }
            if (byteValidateLength == 3 && NumberUtil.equals(fieldValueLong, 65535L)) {
                return Dict.BYTE_INVALID_VALUE;
            }
            if (byteValidateLength == 4 && NumberUtil.equals(fieldValueLong, 4294967294L)) {
                return Dict.BYTE_ERROR_VALUE;
            }
            if (byteValidateLength == 4 && NumberUtil.equals(fieldValueLong, 4294967295L)) {
                return Dict.BYTE_INVALID_VALUE;
            }
        }

        //找到后缀
        String suffix = annotationValue.suffix();

        //找dictCode
        String dictCode = annotationValue.dictCode().equals(DictMapping.AUTO_DICT_CODE)
                ? sourcefield.getName()
                : annotationValue.dictCode();

        //根据dictCode  dictKey 找到 dictValue
        String result = Dict.searchDictValueOrSelf(dictCode, fieldValue);
        return result == null ? null : result + suffix;
    }


}

package dst.v2x.business.slv.service.common.enums;

import dst.v2x.business.slv.service.common.utils.StringUtilsWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.Method;

public interface BaseEnum extends Serializable {

    Logger logger = LoggerFactory.getLogger(BaseEnum.class);

    Integer getCode();

    String getDesc();

    /**
     * 根据code获枚举对象
     *
     * @param value
     * @param enumClazz
     * @param <E>
     * @return
     */
    static <E extends Enum<E> & BaseEnum> E valueOf(Integer value, Class<BaseEnum> enumClazz) {
        Assert.notNull(value, "value不能为空");
        Assert.notNull(enumClazz, "enumClazz不能为空");
        try {
            Method method = enumClazz.getMethod("values");
            BaseEnum[] values = (BaseEnum[]) method.invoke(enumClazz);
            for (BaseEnum baseEnum : values) {
                if (baseEnum.getCode().equals(value)) {
                    return (E) baseEnum;
                }
                if (baseEnum.getDesc().equals(value)) {
                    return (E) baseEnum;
                }

                if (StringUtilsWrapper.equals(((Enum) baseEnum).name(), value)) {
                    return (E) baseEnum;
                }
            }
        } catch (Exception e) {
            logger.error("枚举转换异常, {}, {}", value, enumClazz, e);
        }
        return null;
    }

    /**
     * 获取枚举描述
     *
     * @return
     */
    static String enumToString(BaseEnum baseEnum) {
        return baseEnum == null ? null : baseEnum.getDesc();
    }

}

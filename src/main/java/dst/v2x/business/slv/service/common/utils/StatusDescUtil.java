package dst.v2x.business.slv.service.common.utils;

import com.dst.steed.vds.common.util.DstJsonUtil;
import dst.v2x.business.slv.service.common.annotation.StatusDesc;
import dst.v2x.business.slv.service.common.enums.BaseEnum;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * 通过@StatusDesc注解获取枚举类描述
 *
 * @author: zy
 * @date: 2024/11/16 14:14
 */
@Slf4j
public class StatusDescUtil {

    /**
     * 通过反射获取对象上@StatusDesc注解，通过枚举类根据fieldName设置值
     *
     * @param object
     * @return
     */
    public static void convertStatusDesc(Object object) {
        if(object == null){
            return;
        }
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                StatusDesc statusDesc = field.getAnnotation(StatusDesc.class);
                if (statusDesc != null) {
                    // 状态值
                    String fieldName = statusDesc.fieldName();
                    Field declaredField = object.getClass().getDeclaredField(fieldName);
                    declaredField.setAccessible(true);

                    // 枚举类
                    Class<BaseEnum> enumClass = (Class<BaseEnum>) statusDesc.enumClass();
                    field.setAccessible(true);
                    Integer fieldValue = (Integer) declaredField.get(object);

                    // 设置状态中文值
                    if (fieldValue != null) {
                        BaseEnum baseEnum = BaseEnum.valueOf(fieldValue, enumClass);
                        if(baseEnum != null){
                            field.set(object, baseEnum.getDesc());
                        }else {
                            field.set(object, null);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取枚举类描述失败{}", DstJsonUtil.toString(object), e);
        }

    }
}

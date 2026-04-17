package dst.v2x.business.slv.service.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 金额小数位数注解
 *
 * @author: zhang yao
 * @date: 2023/4/28 15:28
 */
@Target({ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BigDecimalScale {

    int value();

}

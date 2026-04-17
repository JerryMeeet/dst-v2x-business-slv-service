package dst.v2x.business.slv.service.common.annotation;


import dst.v2x.business.slv.service.common.enums.BaseEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface StatusDesc {
    String fieldName();
    Class<? extends BaseEnum> enumClass();
}

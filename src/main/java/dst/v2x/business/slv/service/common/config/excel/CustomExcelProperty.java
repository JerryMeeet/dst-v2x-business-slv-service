package dst.v2x.business.slv.service.common.config.excel;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CustomExcelProperty {
    String value() default "";

    String dateFormat() default "yyyy-MM-dd HH:mm:ss";

    String pattern() default "";

    boolean strFormat() default false;

    int width() default 8;

    /**
     * Defines the sort order for an column.
     * <p>
     * priority: index &gt; order &gt; default sort
     *
     * @return Order of column
     */
    int order() default 1000;

}

package dst.v2x.business.slv.service.infrastructure.biz.dict;

import java.lang.annotation.*;

/**
 * Created by jiangyue on 2023-11-24 18:13:06
 * <p>
 * <p>
 * 字典解析流程   校验报文(可选) -->  翻译字典 ---> 添加后缀
 * 如果启用了报文校验, 报文校验失败时则不进行后续动作, 且直接返回"异常"或者"无效"
 *
 * @author JY
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DictMapping {

    public static final String AUTO_DICT_CODE = "AUTO";

    /**
     * 字典类型编码 如果为空，则使用字段名
     *
     * @return
     */
    String dictCode() default AUTO_DICT_CODE;

    /**
     * 固定后缀
     *
     * @return
     */
    String suffix() default "";

    /**
     * 字节校验长度  用于(车辆报文数据校验)校验报文是否符合长度要求, 如果不符合要求则替换为"异常"或者"无效"
     * <p>
     * -1 表示不校验
     * 1  254异常、255无效
     * 2  4094异常、4095异常,
     * 3  65534异常、65535无效,
     * 4  4294967294异常、4294967295异常
     *
     * @return
     */
    int byteValidateLength() default -1;

    Class<?> fieldTranslator() default DefaultDictFieldTranslator.class;

}

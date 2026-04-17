package dst.v2x.business.slv.service.common.config.excel;



import dst.v2x.business.slv.service.common.constant.ExcelConst;

import java.lang.annotation.*;

/**
 * @Author zhang
 * @create 2024/12/17 16:27
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CustomExcelExport {

    /**
     * 模块名称
     **/
    String moduleName() default "";

    /**
     * 附件名称
     **/
    String fileName();

    /**
     * 数据请求地址
     **/
    String dataUrl();

    /**
     * 文件类型
     **/
    String fileType() default ExcelConst.EXCEL_2007;

    /**
     * 文件类型，默认excel导出
     **/
    int fileClassification() default ExcelConst.FILE_CLASSIFICATION_0;

    /**
     * 取样字段名称
     **/
    String field() default "";

    /**
     * 是否动态附件名称
     **/
    boolean isDynamicFileName() default false;

    /**
     * 单个excel存放数据的最大数量
     **/
    int excelDataSize() default 100000;

    /**
     * 单次请求数量
     **/
    int transmissionSize() default 10000;

    /**
     * 请求参数类型
     **/
    String requestParamsType() default ExcelConst.REQUEST_PARAMS_TYPE_PAGE;

}

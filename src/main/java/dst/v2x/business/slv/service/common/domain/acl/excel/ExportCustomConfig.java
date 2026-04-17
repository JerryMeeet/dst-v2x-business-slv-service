package dst.v2x.business.slv.service.common.domain.acl.excel;

import lombok.Getter;
import lombok.Setter;

/**
 * 导出用户自定义配置
 */
@Getter
@Setter
public class ExportCustomConfig {

    /**
     * 单个excel存放的数据量
     **/
    private Integer excelDataSize;
    /**
     * 单次请求数量
     **/
    private Integer transmissionSize;
    /**
     * 请求参数类型
     **/
    private String requestParamsType;
}

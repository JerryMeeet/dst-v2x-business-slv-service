package dst.v2x.business.slv.service.common.domain.acl.excel;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author zhang
 * @create 2024/12/17 17:12
 * <p>
 * 导出通用DTO
 */
@Getter
@Setter
public class ExportCommonDTO {

    private String moduleName;

    private String requestUrl;

    private String requestParam;
}

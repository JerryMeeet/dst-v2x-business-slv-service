package dst.v2x.business.slv.service.common.domain.acl.excel;

import lombok.Getter;
import lombok.Setter;

/**
 * 导出服务Post请求对象
 */
@Getter
@Setter
public class ExportRequest {

    private String token;

    private Object body;
}

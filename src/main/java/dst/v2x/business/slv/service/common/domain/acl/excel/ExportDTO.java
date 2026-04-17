package dst.v2x.business.slv.service.common.domain.acl.excel;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author zhang
 * @create 2024/12/16 10:11
 */
@Getter
@Setter
public class ExportDTO {

    private Integer index;

    private String queryJson;

    private Long userId;

    private Long orgId;

    private String userName;
}

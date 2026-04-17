package dst.v2x.business.slv.service.infrastructure.biz.archive.dto;

import com.dst.steed.vds.common.domain.request.PageQuery;
import lombok.Data;

/**
 * 站点信息分页查询入参
 *
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 **/
@Data
public class StationInfoPageQueryDTO extends PageQuery {

    /**
     * 站点编号
     */
    private String stationNo;

    /**
     * 站点名称
     */
    private String stationName;
}

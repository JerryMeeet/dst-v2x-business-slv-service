package dst.v2x.business.slv.service.infrastructure.biz.archive.dto;

import com.dst.steed.vds.common.domain.request.PageQuery;
import lombok.Data;

/**
 * 路线分页查询入参
 *
 * @author: pengyunlin
 * @date: 2025/8/18 15:51
 **/
@Data
public class MapMakePageQueryDTO extends PageQuery {

    /**
     * 路线名称
     */
    private String mapMakeName;

    /**
     * 路线状态 1.已完成交付 2.新建 3.测试中 4.采图中
     */
    private Integer status;
}

package dst.v2x.business.slv.service.infrastructure.acl.zelos.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 九识无人车响应分页数据
 *
 * @author 江民来
 * @date 2025年02月10日 15:35
 */
@Getter
@Setter
@ToString
public class ZelosPageRes {
    /**
     * 总⻚数
     */
    private Integer totalPages;

    /**
     * 总条数
     */
    private Integer totalNum;

    /**
     * 每页条数
     */
    private Integer limit;

    /**
     * 当前页
     */
    private Integer currentPage;

    /**
     * 车辆编号集合
     */
    private List<String> vehicles;

    /**
     * 站点编号集合
     */
    private List<String> siteNos;

    /**
     * 任务编号集合
     */
    private List<String> taskNos;
}

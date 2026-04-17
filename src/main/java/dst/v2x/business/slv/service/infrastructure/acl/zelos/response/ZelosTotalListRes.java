package dst.v2x.business.slv.service.infrastructure.acl.zelos.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 九识无人车响应基础类
 */
@Getter
@Setter
@ToString
public class ZelosTotalListRes<T> {
    /**
     * 总数
     */
    private Integer total;

    /**
     * 数据集合
     */
    private List<T> list;
}

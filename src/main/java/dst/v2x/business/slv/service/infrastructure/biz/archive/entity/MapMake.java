package dst.v2x.business.slv.service.infrastructure.biz.archive.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import dst.v2x.business.slv.service.common.domain.base.BaseDomain;
import lombok.Data;

/**
 * 路线表
 * @TableName slv_map_make
 */
@Data
@TableName(value ="slv_map_make")
public class MapMake extends BaseDomain {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 路线ID
     */
    private Integer mapMakeId;

    /**
     * 路线名称
     */
    private String mapMakeName;

    /**
     * 路线省份
     */
    private String provinceName;

    /**
     * 所属公司 1.白犀牛, 2.九识, 3.新石器
     */
    private Integer belongCompany;

    /**
     * 路线城市
     */
    private String cityName;

    /**
     * 路线区域
     */
    private String districtName;

    /**
     * 路线状态 1.已完成交付 2.新建 3.测试中 4.采图中
     */
    private Integer status;
}
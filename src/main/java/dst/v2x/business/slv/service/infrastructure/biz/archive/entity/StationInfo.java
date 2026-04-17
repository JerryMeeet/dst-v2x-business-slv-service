package dst.v2x.business.slv.service.infrastructure.biz.archive.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import dst.v2x.business.slv.service.common.domain.base.BaseDomain;
import lombok.Data;

/**
 * 站点信息表
 * @TableName slv_station_info
 */
@Data
@TableName(value ="slv_station_info")
public class StationInfo extends BaseDomain {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 站点编号
     */
    private String stationNo;

    /**
     * 站点名称
     */
    private String stationName;

    /**
     * 站点地址
     */
    private String stationAddress;

    /**
     * 所属公司 1.白犀牛, 2.九识, 3.新石器
     */
    private Integer belongCompany;

    /**
     * 站点纬度
     */
    private Double lat;

    /**
     * 站点经度
     */
    private Double lng;
}
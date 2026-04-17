package dst.v2x.business.slv.service.infrastructure.acl.zelos.response;

import lombok.Data;

/**
 * @author 江民来
 * @date 2025年02月10日 17:48
 */
@Data
public class QueryZelosCarListRes {

    /**
     * 车辆 id
     */
    private Integer id;

    /**
     * 车辆名称
     */
    private String name;

    /**
     * 车辆牌照
     */
    private String number;

    /**
     * 车架号
     */
    private String vin;

    /**
     * 站点 id
     */
    private Integer stationId;

    /**
     * 站点名称
     */
    private String stationName;

    /**
     * 业务状态
     */
    private String businessStatus;

    /**
     * 业务状态名称
     */
    private String businessStatusName;



}

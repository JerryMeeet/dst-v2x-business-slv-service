package dst.v2x.business.slv.service.infrastructure.acl.neolix.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 新石器站点信息返回对象
 */
@Data
public class NeolixStationInfoRes {
    /**
     * 站点ID
     */
    private Integer stationId;

    /**
     * 站点名称
     */
    private String stationName;

    /**
     * 站点地址
     */
    private String stationAddress;

    /**
     * 经度
     */
    @JsonProperty("lon")
    private Double lng;

    /**
     * 纬度
     */
    private Double lat;
}

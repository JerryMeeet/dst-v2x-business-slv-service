package dst.v2x.business.slv.service.infrastructure.acl.neolix.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author: zy
 * @date: 2025/5/19 11:43
 */
@Data
public class NeolixVehicleListRes {

    /**
     * 车架号
     */
    @JsonProperty("vin")
    private String vin;

    /**
     * 车架号id
     */
    @JsonProperty("vinId")
    private String vinId;

    /**
     * 车架号编码
     */
    @JsonProperty("vinCode")
    private String vinCode;
}

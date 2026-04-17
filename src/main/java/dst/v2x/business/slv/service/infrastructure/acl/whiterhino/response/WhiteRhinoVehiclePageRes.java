package dst.v2x.business.slv.service.infrastructure.acl.whiterhino.response;

import com.dst.steed.vds.common.domain.response.PageDTO;
import lombok.Data;

import java.util.List;

/**
 * 白犀牛-获取车辆分页列表出参
 *
 * @author: zy
 * @date: 2024/11/8 10:33
 */
@Data
public class WhiteRhinoVehiclePageRes extends PageDTO {

    private List<String> vehicles;
}

package dst.v2x.business.slv.service.common.utils.amap;

import lombok.Data;

@Data
public class Regeocode {
    private AddressComponent addressComponent;
    private String formatted_address;
}

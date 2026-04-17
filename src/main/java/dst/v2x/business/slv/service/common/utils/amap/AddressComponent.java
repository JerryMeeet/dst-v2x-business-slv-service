package dst.v2x.business.slv.service.common.utils.amap;

import lombok.Data;

@Data
public class AddressComponent {
    private Object city;
    private String province;
    private Object adcode;
    private Object district;
    private Object towncode;
    private Object country;
    private Object township;

    public String getCityName() {
        if (city instanceof String) {
            return String.valueOf(city);
        } else {
            return province;
        }
    }
}

package dst.v2x.business.slv.service.infrastructure.acl.zelos.response;

import lombok.Data;

/**
 * @author pengyl
 * @date 2025/8/8
 */
@Data
public class ZelosMapMakeRes {
    /**
     * 路线名称
     */
    private String name;

    /**
     * 路线唯一id
     */
    private Integer id;

    /**
     * 路线省份
     */
    private String province;

    /**
     * 路线城市
     */
    private String city;

    /**
     * 路线区域
     */
    private String district;

    /**
     * 路线状态
     */
    private String status;
}

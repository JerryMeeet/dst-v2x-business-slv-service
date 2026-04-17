package dst.v2x.business.slv.service.infrastructure.acl.zelos.response;

import lombok.Data;

/**
 * 九识站点信息-出参
 */
@Data
public class ZelosSiteInfoRes {
    /**
     * 站点的编号确保唯一
     */
    private String siteNo;

    /**
     * 站点的名称
     */
    private String siteName;

    /**
     * 站点地址
     */
    private String siteAddress;

    /**
     * 位置纬度坐标系：高德⽕星坐标
     */
    private String lat;

    /**
     * 位置经度坐标系：高德⽕星坐标
     */
    private String lng;
}

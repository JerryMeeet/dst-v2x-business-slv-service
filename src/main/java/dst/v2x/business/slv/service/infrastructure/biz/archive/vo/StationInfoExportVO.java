package dst.v2x.business.slv.service.infrastructure.biz.archive.vo;

import dst.v2x.business.slv.service.common.config.excel.CustomExcelProperty;
import lombok.Data;

/**
 * 站点信息导出类
 */
@Data
public class StationInfoExportVO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 站点编号
     */
    @CustomExcelProperty(value = "站点编号")
    private String stationNo;

    /**
     * 站点名称
     */
    @CustomExcelProperty(value = "站点名称")
    private String stationName;


    /**
     * 车辆品牌
     */
    @CustomExcelProperty(value = "车辆品牌")
    private String belongCompanyName;

    /**
     * 站点地址
     */
    @CustomExcelProperty(value = "站点地址")
    private String stationAddress;

    /**
     * 站点纬度
     */
    @CustomExcelProperty(value = "站点纬度")
    private Double lat;

    /**
     * 站点经度
     */
    @CustomExcelProperty(value = "站点经度")
    private Double lng;
}

package dst.v2x.business.slv.service.infrastructure.biz.archive.vo;

import dst.v2x.business.slv.service.common.config.excel.CustomExcelProperty;
import lombok.Data;

/**
 * 路线导出类
 */
@Data
public class MapMakeExportVO {
    /**
     * 路线名称
     */
    @CustomExcelProperty(value = "路线名称")
    private String mapMakeName;

    /**
     * 路线ID
     */
    @CustomExcelProperty(value = "路线ID")
    private Integer mapMakeId;

    /**
     * 路线省份
     */
    @CustomExcelProperty(value = "路线省份")
    private String provinceName;

    /**
     * 路线城市
     */
    @CustomExcelProperty(value = "路线城市")
    private String cityName;

    /**
     * 路线区域
     */
    @CustomExcelProperty(value = "路线区域")
    private String districtName;

    /**
     * 路线状态
     */
    @CustomExcelProperty(value = "路线状态")
    private String statusName;
}

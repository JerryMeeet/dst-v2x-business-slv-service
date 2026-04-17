package dst.v2x.business.slv.service.infrastructure.biz.archive.vo;

import dst.v2x.business.slv.service.common.config.excel.CustomExcelProperty;
import lombok.Data;

/**
 * 任务信息导出类
 */
@Data
public class TaskInfoExportVO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 任务编号
     */
    @CustomExcelProperty(value = "任务编号")
    private String taskNo;

    /**
     * 车辆VIN
     */
    @CustomExcelProperty(value = "车辆VIN")
    private String vin;

    /**
     * 车辆品牌
     */
    @CustomExcelProperty(value = "车辆品牌")
    private String belongCompanyName;

    /**
     * 任务状态
     */
    @CustomExcelProperty(value = "任务状态")
    private String taskStatusName;

//    /**
//     * 开始时间
//     */
//    @CustomExcelProperty(value = "开始时间")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    private LocalDateTime startTime;
//
//    /**
//     * 结束时间
//     */
//    @CustomExcelProperty(value = "结束时间")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    private LocalDateTime endTime;
}

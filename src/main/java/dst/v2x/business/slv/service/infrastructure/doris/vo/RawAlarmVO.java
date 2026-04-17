package dst.v2x.business.slv.service.infrastructure.doris.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import dst.v2x.business.slv.service.common.annotation.StatusDesc;
import dst.v2x.business.slv.service.common.config.excel.CustomExcelProperty;
import dst.v2x.business.slv.service.common.enums.raw.RawAlarmTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 上报报警数据-出参
 */
@Data
public class RawAlarmVO {
    /**
     * 车辆编号
     */
    @CustomExcelProperty(value = "车辆编号")
    private String vehicleNo;

    /**
     * 数据类型
     */
    private Integer dataType;
    /**
     * 数据类型名称
     */
    @CustomExcelProperty(value = "报警类型")
    @StatusDesc(fieldName = "dataType", enumClass = RawAlarmTypeEnum.class)
    private String dataTypeName;

    /**
     * 数据时间
     */
    @CustomExcelProperty(value = "报警时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime dataTime;

    /**
     * 急停时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime emergencyStopTime;

    /**
     * 解决时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime doneTime;
}
package dst.v2x.business.slv.service.infrastructure.doris.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

/**
 * CAN数据导航
 * @TableName slv_can_data_nav
 */
@Data
@TableName(value ="slv_can_data_nav")
public class CanDataNav {
    /**
     * 车辆编号
     */
    private String vehicleNo;

    /**
     * 年份
     */
    @JsonFormat(pattern = "yyyy", timezone = "GMT+8")
    private LocalDate year;

    /**
     * 月份
     */
    private Integer month;

    /**
     * 日期
     */
    private Integer day;

    /**
     * hour
     */
    private Integer hour;
}
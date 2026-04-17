package dst.v2x.business.slv.service.infrastructure.biz.archive.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 站点信息分页查询出参
 *
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 */
@Data
public class StationInfoPageQueryVO {
    /**
     * 主键
     */
    private String id;

    /**
     * 站点编号
     */
    private String stationNo;

    /**
     * 站点名称
     */
    private String stationName;

    /**
     * 站点地址
     */
    private String stationAddress;

    /**
     * 所属公司 1.白犀牛, 2.九识, 3.新石器
     */
    private Integer belongCompany;
    private String belongCompanyName;

    /**
     * 站点纬度
     */
    private Double lat;

    /**
     * 站点经度
     */
    private Double lng;

    /**
     * 删除标记 0正常 1删除
     */
    private Integer isDeleted;

    /**
     * 创建人id
     */
    private Long creator;

    /**
     * 创建人
     */
    private String creatorName;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    private Long modifier;

    /**
     * 修改人
     */
    private String modifierName;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime modifyTime;
}

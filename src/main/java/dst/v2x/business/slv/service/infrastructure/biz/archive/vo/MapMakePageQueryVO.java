package dst.v2x.business.slv.service.infrastructure.biz.archive.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import dst.v2x.business.slv.service.common.annotation.StatusDesc;
import dst.v2x.business.slv.service.common.enums.archive.MapMakeStatusEnum;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 路线分页查询出参
 *
 * @author: pengyunlin
 * @date: 2025/8/18 15:51
 */
@Data
public class MapMakePageQueryVO {
    /**
     * 主键
     */
    private String id;

    /**
     * 路线ID
     */
    private Integer mapMakeId;

    /**
     * 路线名称
     */
    private String mapMakeName;

    /**
     * 路线省份
     */
    private String provinceName;

    /**
     * 所属公司 1.白犀牛, 2.九识, 3.新石器
     */
    private Integer belongCompany;
    @StatusDesc(fieldName = "belongCompany", enumClass = VehicleCompanyEnum.class)
    private String belongCompanyName;

    /**
     * 路线城市
     */
    private String cityName;

    /**
     * 路线区域
     */
    private String districtName;

    /**
     * 路线状态 1.已完成交付 2.新建 3.测试中 4.采图中
     */
    private Integer status;
    @StatusDesc(fieldName = "status", enumClass = MapMakeStatusEnum.class)
    private String statusName;

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

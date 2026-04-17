package dst.v2x.business.slv.service.infrastructure.biz.basicexporttask.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文件导出任务表
 * </p>
 *
 * @author 张荃
 * @since 2024-10-14
 */
@TableName("basic_export_task")
public class BasicExportTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 数据标题：设备号,VIN码,围栏名等
     */
    @TableField("data_title")
    private String dataTitle;

    /**
     * 机构ID
     */
    @TableField("org_id")
    private Long orgId;

    /**
     * oss文件id
     */
    @TableField("oss_file_id")
    private Long ossFileId;

    /**
     * 文件名 冗余
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 文件路径名冗余
     */
    @TableField("file_path")
    private String filePath;

    /**
     * 文件类型 字典downloadFileType 1-原始数据，2-历史数据3-设备数据4-车辆数据5-围栏数据等
     */
    @TableField("file_type")
    private String fileType;

    /**
     * 任务开始日期
     */
    @TableField("start_time")
    private LocalDateTime startTime;

    /**
     * 任务结束日期
     */
    @TableField("end_time")
    private LocalDateTime endTime;

    /**
     * 数据查询开始时间，有就存
     */
    @TableField("query_start_time")
    private LocalDateTime queryStartTime;

    /**
     * 数据查询结束时间，有就存
     */
    @TableField("query_end_time")
    private LocalDateTime queryEndTime;

    /**
     * 数据查询全部条件，有就存
     */
    @TableField("query_condition")
    private String queryCondition;

    /**
     * 下载状态 字典 downloadStatus 0-失败 1-成功，2-正在生成
     */
    @TableField("status")
    private String status;

    /**
     * 是否已删除状态 字典commonWhether
     */
    @TableField("del_flag")
    private String delFlag;

    /**
     * 创建人
     */
    @TableField("creator_id")
    private Long creatorId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 创建人名称
     */
    @TableField("creator_name")
    private String creatorName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataTitle() {
        return dataTitle;
    }

    public void setDataTitle(String dataTitle) {
        this.dataTitle = dataTitle;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getOssFileId() {
        return ossFileId;
    }

    public void setOssFileId(Long ossFileId) {
        this.ossFileId = ossFileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getQueryStartTime() {
        return queryStartTime;
    }

    public void setQueryStartTime(LocalDateTime queryStartTime) {
        this.queryStartTime = queryStartTime;
    }

    public LocalDateTime getQueryEndTime() {
        return queryEndTime;
    }

    public void setQueryEndTime(LocalDateTime queryEndTime) {
        this.queryEndTime = queryEndTime;
    }

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    @Override
    public String toString() {
        return "BasicExportTask{" +
               "id=" + id +
               ", dataTitle=" + dataTitle +
               ", orgId=" + orgId +
               ", ossFileId=" + ossFileId +
               ", fileName=" + fileName +
               ", filePath=" + filePath +
               ", fileType=" + fileType +
               ", startTime=" + startTime +
               ", endTime=" + endTime +
               ", queryStartTime=" + queryStartTime +
               ", queryEndTime=" + queryEndTime +
               ", queryCondition=" + queryCondition +
               ", status=" + status +
               ", delFlag=" + delFlag +
               ", creatorId=" + creatorId +
               ", createTime=" + createTime +
               ", creatorName=" + creatorName +
               "}";
    }
}

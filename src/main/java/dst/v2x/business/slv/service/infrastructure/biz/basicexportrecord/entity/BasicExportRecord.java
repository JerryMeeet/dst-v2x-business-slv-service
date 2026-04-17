package dst.v2x.business.slv.service.infrastructure.biz.basicexportrecord.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 导出OSS记录
 * </p>
 *
 * @author 陈新
 * @since 2025-06-05
 */
@TableName("basic_export_record")
public class BasicExportRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 冗余机构
     */
    @TableField("org_id")
    private Long orgId;

    /**
     * 所属模块
     */
    @TableField("module")
    private Integer module;

    /**
     * 模块名称
     */
    @TableField("module_name")
    private String moduleName;

    /**
     * 请求url
     */
    @TableField("request_url")
    private String requestUrl;

    /**
     * 请求方式
     */
    @TableField("request_param")
    private String requestParam;

    /**
     * 请求方式（GET，POST）
     */
    @TableField("request_method")
    private String requestMethod;

    /**
     * 文件oss
     */
    @TableField("file_id")
    private Long fileId;

    /**
     * 文件名称
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 文件地址
     */
    @TableField("file_url")
    private String fileUrl;

    /**
     * 文件类型
     */
    @TableField("file_type")
    private String fileType;

    /**
     * 文件分类（0-文档 1-附件）
     */
    @TableField("file_classification")
    private Integer fileClassification;

    /**
     * 来源 1-运营2.0导出 2-php导出
     */
    @TableField("source")
    private Integer source;

    /**
     * 状态：0-待处理，1-处理中，2-处理完成，3-处理失败，4-未获取到数据
     */
    @TableField("status")
    private Integer status;

    /**
     * 自定义配置（单个excel总数量-excelDataSize，单次请求数量-transmissionSize）
     */
    @TableField("custom_config")
    private String customConfig;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 创建人id
     */
    @TableField("creator_id")
    private Long creatorId;

    /**
     * 操作人名称
     */
    @TableField("creator_name")
    private String creatorName;

    /**
     * 操作时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新人Id
     */
    @TableField("modifier_id")
    private Long modifierId;

    /**
     * 更新人名称
     */
    @TableField("modifier_name")
    private String modifierName;

    /**
     * 更新时间
     */
    @TableField("modify_time")
    private LocalDateTime modifyTime;

    /**
     * 数据取样
     */
    @TableField("sampling")
    private String sampling;

    @TableField("server_name")
    private String serverName;

    /**
     * 取样的字段
     */
    @TableField("sampling_field")
    private String samplingField;

    /**
     * 下载次数
     */
    @TableField("downloads")
    private Integer downloads;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
    public Integer getModule() {
        return module;
    }

    public void setModule(Integer module) {
        this.module = module;
    }
    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }
    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }
    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }
    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    public Integer getFileClassification() {
        return fileClassification;
    }

    public void setFileClassification(Integer fileClassification) {
        this.fileClassification = fileClassification;
    }
    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getCustomConfig() {
        return customConfig;
    }

    public void setCustomConfig(String customConfig) {
        this.customConfig = customConfig;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public Long getModifierId() {
        return modifierId;
    }

    public void setModifierId(Long modifierId) {
        this.modifierId = modifierId;
    }
    public String getModifierName() {
        return modifierName;
    }

    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }
    public String getSampling() {
        return sampling;
    }

    public void setSampling(String sampling) {
        this.sampling = sampling;
    }
    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
    public String getSamplingField() {
        return samplingField;
    }

    public void setSamplingField(String samplingField) {
        this.samplingField = samplingField;
    }
    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return "BasicExportRecord{" +
               "id=" + id +
               ", orgId=" + orgId +
               ", module=" + module +
               ", moduleName=" + moduleName +
               ", requestUrl=" + requestUrl +
               ", requestParam=" + requestParam +
               ", requestMethod=" + requestMethod +
               ", fileId=" + fileId +
               ", fileName=" + fileName +
               ", fileUrl=" + fileUrl +
               ", fileType=" + fileType +
               ", fileClassification=" + fileClassification +
               ", source=" + source +
               ", status=" + status +
               ", customConfig=" + customConfig +
               ", remark=" + remark +
               ", creatorId=" + creatorId +
               ", creatorName=" + creatorName +
               ", createTime=" + createTime +
               ", modifierId=" + modifierId +
               ", modifierName=" + modifierName +
               ", modifyTime=" + modifyTime +
               ", sampling=" + sampling +
               ", serverName=" + serverName +
               ", samplingField=" + samplingField +
               ", downloads=" + downloads +
               "}";
    }
}

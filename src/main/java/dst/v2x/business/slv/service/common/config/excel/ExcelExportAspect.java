package dst.v2x.business.slv.service.common.config.excel;

import cn.hutool.core.util.StrUtil;
import com.dst.steed.vds.common.base.domain.user.SSOUser;
import com.dst.steed.vds.common.base.exception.BusinessException;
import com.dst.steed.vds.common.constant.MinConstant;
import com.dst.steed.vds.common.domain.response.RespType;
import com.dst.steed.vds.common.domain.response.Response;
import com.dst.steed.vds.common.util.DstJsonUtil;
import com.dst.steed.vds.common.util.DstThreadLocalUtil;
import com.dst.steed.vds.common.util.NullSafeUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dst.v2x.business.slv.service.common.constant.ExcelConst;
import dst.v2x.business.slv.service.common.domain.acl.excel.ExportCustomConfig;
import dst.v2x.business.slv.service.common.domain.acl.excel.ExportRequest;
import dst.v2x.business.slv.service.common.enums.base.DownloadStatusEnum;
import dst.v2x.business.slv.service.common.utils.DstUserUtil;
import dst.v2x.business.slv.service.infrastructure.biz.basicexportrecord.entity.BasicExportRecord;
import dst.v2x.business.slv.service.infrastructure.biz.basicexportrecord.mapper.BasicExportRecordMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

@Slf4j
@Aspect
@Component
public class ExcelExportAspect {

    private static final String EXPORT_EXCEL_TOPIC = "dst_export_excel_topic";
    private static final String EXPORT_FILE_TOPIC = "dst_export_file_topic";

    @Autowired
    private RocketMQTemplate rocketMqTemplateDefault;

    @Autowired
    private BasicExportRecordMapper basicExportRecordMapper;

    @Autowired
    private HttpServletRequest request;

    @Value("${spring.application.name}")
    private String serverName;

    // 定义切点，拦截所有使用@CustomExcelExport注解的方法
    @Pointcut("@annotation(dst.v2x.business.slv.service.common.config.excel.CustomExcelExport)")
    public void excelExportPointcut() {
    }


    // 在方法正常返回之后执行
    @AfterReturning(pointcut = "excelExportPointcut()", returning = "result")
    public void afterReturningExcelExport(JoinPoint joinPoint, Response<?> result) throws Exception {
        if (RespType.SUCCESS.getCode() == result.getCode()) {// 只有返回结果为成功才加入导出
            HttpServletRequest request = DstThreadLocalUtil.getRequest();
            // 登陆用户信息
            String token = DstUserUtil.getUserToken();
            SSOUser ssoUser = DstUserUtil.getUser().orElseThrow(() -> new BusinessException("没有用户!"));
            // 方法注解信息
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            CustomExcelExport excelExport = method.getAnnotation(CustomExcelExport.class);
            String requestParams = "";
            // 根据请求方式处理请求参数
            if (RequestMethod.POST.name().equals(request.getMethod())) {
                Object[] args = joinPoint.getArgs();
                Object object = args[0];
                // 设置isExport为true，用于导出时判断是否导出
                String bodyStr = DstJsonUtil.toString(object);
                ObjectMapper mapper = DstJsonUtil.getMapper();
                JsonNode bodyNode = mapper.readTree(bodyStr);
                ObjectNode bodyObj = (ObjectNode) bodyNode;
                bodyObj.put("isExport", true);

                ExportRequest exportRequest = new ExportRequest();
                exportRequest.setToken(token);
                exportRequest.setBody(bodyObj);
                requestParams = DstJsonUtil.toString(exportRequest);
            } else if (RequestMethod.GET.name().equals(request.getMethod())) {
                requestParams = replaceRequestParams(request.getQueryString());
                if (requestParams == null) {
                    requestParams = "token=" + token;
                } else if (!requestParams.contains("token")) {
                    requestParams += "&token=" + token;
                }
            }

            String dateStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// 文件带上当前时间
            // 设置附件名
            BasicExportRecord exportRecord = getExportRecord(excelExport, result, requestParams, dateStr, ssoUser);
            send2Queue(saveExportRecord(exportRecord), excelExport.fileClassification());
        }
    }

    /* 获取导出记录 */
    private BasicExportRecord getExportRecord(CustomExcelExport ossExport, Response<?> result, String requestParams, String dateStr, SSOUser ssoUser) {
        String fileName;
        String data = (String) result.getData();
        if (ossExport.isDynamicFileName() && StrUtil.isNotBlank(data)) {
            fileName = data;
        } else {
            fileName = ossExport.fileName();
        }
        ExportCustomConfig customConfig = new ExportCustomConfig();
        customConfig.setExcelDataSize(ossExport.excelDataSize());
        customConfig.setTransmissionSize(ossExport.transmissionSize());
        customConfig.setRequestParamsType(ossExport.requestParamsType());
        BasicExportRecord exportRecord = new BasicExportRecord();
        exportRecord.setModuleName(ossExport.moduleName());
        exportRecord.setRequestUrl(ossExport.dataUrl());
        exportRecord.setRequestParam(requestParams);
        exportRecord.setRequestMethod(request.getMethod());
        exportRecord.setFileName(fileName + "-T" + dateStr);
        exportRecord.setFileType(ossExport.fileType());
        exportRecord.setFileClassification(ossExport.fileClassification());
        exportRecord.setSource(ExcelConst.SOURCE_1);
        exportRecord.setStatus(DownloadStatusEnum.STATUS_0.getValue());
        exportRecord.setCreatorId(ssoUser.getId());
        exportRecord.setCreatorName(ssoUser.getUsername() + "/" + ssoUser.getRealname());
        exportRecord.setCreateTime(LocalDateTime.now());
        exportRecord.setModifierId(ssoUser.getId());
        exportRecord.setModifierName(ssoUser.getUsername() + "/" + ssoUser.getRealname());
        exportRecord.setModifyTime(LocalDateTime.now());
        exportRecord.setServerName(serverName);
        exportRecord.setSamplingField(ossExport.field());
        // 自定义配置
        NullSafeUtil.object(customConfig).ifPresent(config ->
                exportRecord.setCustomConfig(DstJsonUtil.toString(config)));

        return exportRecord;
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(MinConstant.HEADER_TOKEN);
        if (StrUtil.isBlank(token)) {
            token = request.getParameter(MinConstant.HEADER_TOKEN);
        }
        return token;
    }

    private Object replaceRequestParamsPost(int pageSize, Object object) {
        if (pageSize <= 0) {
            return object;
        }

        String string = DstJsonUtil.toString(object);
        try {
            ObjectMapper mapper = DstJsonUtil.getMapper();
            JsonNode jn = mapper.readTree(string);
            ObjectNode jnObj = (ObjectNode) jn;
            jnObj.put("exportPageSize", pageSize);
            object = jnObj;
        } catch (Exception e) {
            log.error("参数转换编码异常：", e);
        }
        return object;
    }

    private String replaceRequestParams(String requestParams) {
        if (StrUtil.isNotBlank(requestParams)) {
            try {
                requestParams = URLDecoder.decode(requestParams, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.error("参数转换编码异常：", e);
            }
            List<String> params = new ArrayList<>();
            StringTokenizer tokenizer = new StringTokenizer(requestParams, "&");
            while (tokenizer.hasMoreTokens()) {
                String str = tokenizer.nextToken();
                if (!str.contains("pageSize") && !str.contains("pageNum")) {// 过滤前端传过来的分页参数
                    params.add(str);
                }
            }
            requestParams = String.join("&", params);
        }
        return requestParams;
    }

    /**
     * 将当前导出请求id发送到队列
     *
     * @param id
     */
    private void send2Queue(Integer id, int fileClassification) {
        String queueName = fileClassification == ExcelConst.FILE_CLASSIFICATION_0 ? EXPORT_EXCEL_TOPIC : EXPORT_FILE_TOPIC;
        log.info("发送导出请求到{}队列，id:{}", queueName, id);
        rocketMqTemplateDefault.convertAndSend(queueName, id.toString());
    }

    private Integer saveExportRecord(BasicExportRecord exportRecord) {
        basicExportRecordMapper.insert(exportRecord);
        return exportRecord.getId();
    }
}

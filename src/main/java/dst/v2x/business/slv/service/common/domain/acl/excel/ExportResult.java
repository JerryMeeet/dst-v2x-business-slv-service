package dst.v2x.business.slv.service.common.domain.acl.excel;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import dst.v2x.business.slv.service.common.config.excel.CustomExcelProperty;
import dst.v2x.business.slv.service.infrastructure.biz.dict.Dict;
import dst.v2x.business.slv.service.infrastructure.biz.dict.DictMapping;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 导出excel数据包装类
 */
@Slf4j
@Getter
@Setter
public class ExportResult<T> {
    /**
     * excel表头
     **/
    private List<List<String>> headers;

    /**
     * 方式一：列数据（excel导出）
     **/
    private List<List<Object>> columns;

    /**
     * 方式二：对象+注解数据（excel导出）
     **/
    private List<T> excelVOS;

    /**
     * 总的分页数量
     **/
    private Long totalPages;

    /**
     * 导出方式一：表头文字，列数据，总页数；兼容最原始的excel导出功能
     **/
    public static <T> ExportResult<T> wrap(List<List<String>> headers,
                                           List<List<Object>> columns,
                                           Long totalPages) {
        ExportResult<T> exportResult = new ExportResult<>();
        exportResult.setHeaders(headers);
        exportResult.setColumns(columns);
        exportResult.setTotalPages(totalPages);
        return exportResult;
    }

    /**
     * 导出方式二：对象数据集合，总分页数，类（需要被解析成表头和列名），未被注解标记的列不需要导出；
     * 此方式最终会被转换成导出方式一，这样job-server只需要一种解析方式
     **/
    public static <T> ExportResult<T> wrap(List<T> excelVOS, Long totalPages,
                                           Class<?> classz, String... ignoreProperties) {
        return covertObject2Map(excelVOS, totalPages, classz, ignoreProperties);
    }

    /**
     * 方式二的对象转换方式一的map
     **/
    private static <T> ExportResult<T> covertObject2Map(List<T> excelVOS, Long totalPages, Class<?> clazz,
                                                        String... ignoreProperties) {
        List<Field> declaredFields = new ArrayList<>();
        while (clazz != null && !clazz.equals(Object.class)) {
            declaredFields.addAll(0, Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> field.getAnnotation(CustomExcelProperty.class) != null).collect(Collectors.toList()));
            clazz = clazz.getSuperclass();
        }
        Set<String> ignores = new HashSet<>();
        if (ignoreProperties != null && ignoreProperties.length > 0) {
            ignores = new HashSet<>(Arrays.asList(ignoreProperties));
        }
        List<List<String>> headers = new ArrayList<>(10);
        List<List<Object>> columns = new ArrayList<>(10);
        Map<Integer, List<String>> sortedMap = new HashMap<>();
        for (Field field : declaredFields) {
            CustomExcelProperty excelName = field.getAnnotation(CustomExcelProperty.class);
            if (excelName == null || ignores.contains(field.getName())) {
                continue;
            }
            List<String> list = sortedMap.computeIfAbsent(excelName.order(), ArrayList::new);

            list.add(excelName.value() + "@" + field.getName());
        }
        sortedMap.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(e -> e.getValue().forEach(v -> {
            String[] arr = v.split("@");
            // 如果是多级表头，会根据"::"分割
            String[] headerArr = arr[0].split("::");
            headers.add(ListUtil.of(headerArr));
        }));
        for (T excelVO : excelVOS) {
            Class invokClzz = excelVO.getClass();
            List<Object> rowData = new ArrayList<>(10);
            for (Field field : declaredFields) {
                CustomExcelProperty excelName = field.getAnnotation(CustomExcelProperty.class);

                if (excelName == null) {
                    continue;
                }
                Object value = null;
                try {
                    String methodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                    value = invokClzz.getMethod(methodName).invoke(excelVO);
                } catch (Exception e) {
                    log.error("获取属性值异常", e);
                }
                if (value instanceof Date) {
                    value = DateUtil.format((Date) value, excelName.dateFormat());
                }
                if (value instanceof LocalDateTime) {
                    value = DateUtil.format((LocalDateTime) value, excelName.dateFormat());
                }
                if (excelName.strFormat() && value != null) {
                    value = String.valueOf(value);
                }
                // 处理字典翻译
                DictMapping dictMapping = field.getAnnotation(DictMapping.class);
                if (dictMapping != null) {
                    // 根据字典类型和字段值进行翻译
                    value = Dict.searchDictValueOrSelf(dictMapping.dictCode(), value);
                }
                rowData.add(value);
            }
            columns.add(rowData);
        }
        ExportResult<T> wrap = wrap(headers, columns, totalPages);
        wrap.setExcelVOS(excelVOS);
        return wrap;
    }

}

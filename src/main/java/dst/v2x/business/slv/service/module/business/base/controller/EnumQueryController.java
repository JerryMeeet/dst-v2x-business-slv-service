// AiAutoGenerateStart_5F3A9B2C8D7E1F0A4C6B8E2D0F1A7C5
package dst.v2x.business.slv.service.module.business.base.controller;

import com.dst.steed.vds.common.domain.response.Response;
import dst.v2x.business.slv.service.common.enums.BaseEnum;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 枚举查询控制器
 * 提供统一接口，根据传入的枚举类名返回其所有枚举值的 code 和 desc。
 */
@RestController
@RequestMapping("/base/enum")
public class EnumQueryController {

    /**
     * 根据枚举类名获取其所有枚举值的 code 和 desc
     * @param enumClassName 枚举类的全限定名
     * @return 枚举值列表
     */
    @GetMapping("/getValues")
    public Response<List<EnumValue>> getValues(@NotNull(message = "enumClassName不能为空") String enumClassName) {
        try {
            Class<?> enumClass = Class.forName("dst.v2x.business.slv.service.common.enums." + enumClassName);
            if (BaseEnum.class.isAssignableFrom(enumClass)) {
                Object[] enumConstants = enumClass.getEnumConstants();
                List<EnumValue> result = new ArrayList<>();
                for (Object constant : enumConstants) {
                    BaseEnum baseEnum = (BaseEnum) constant;
                    result.add(new EnumValue(baseEnum.getCode(), baseEnum.getDesc()));
                }
                return Response.succeed(result);
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            // 处理异常情况，如类未找到或类型转换失败
            return Response.succeed(new ArrayList<>());
        }
        return Response.succeed(new ArrayList<>());
    }

    /**
     * 枚举值 DTO
     * 用于封装每个枚举值的 code 和 desc。
     */
    private static class EnumValue {
        private final Integer code;
        private final String desc;

        public EnumValue(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }
}
// AiAutoGenerateEnd_5F3A9B2C8D7E1F0A4C6B8E2D0F1A7C5
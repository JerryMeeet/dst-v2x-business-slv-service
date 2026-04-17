package dst.v2x.business.slv.service.common.utils;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author pengyl
 * @date 2025/6/24
 * @description Job工具类
 */
public class JobUtils {
    /**
     * 供应商相关Job是否执行
     * @param jobParam            Job参数
     * @param vehicleCompanyEnum 无人车企业编码
     * @return true：执行，false：不执行
     */
    public static boolean executeSupplierJob(String jobParam, VehicleCompanyEnum vehicleCompanyEnum){
        if(StringUtils.isBlank(jobParam)){
            return false;
        }
        //将jobParam转为企业编码集合
        List<Integer> vehicleCompanyCodes = JSONUtil.toList(jobParam, Integer.class);
        if(CollectionUtils.isEmpty(vehicleCompanyCodes)){
            return false;
        }
        //判断企业编码集合中是否包含当前企业编码
        return vehicleCompanyCodes.contains(vehicleCompanyEnum.getCode());
    }
}

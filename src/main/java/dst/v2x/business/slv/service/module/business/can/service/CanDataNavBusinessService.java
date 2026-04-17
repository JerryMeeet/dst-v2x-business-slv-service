package dst.v2x.business.slv.service.module.business.can.service;

import cn.hutool.core.date.DateUtil;
import dst.v2x.business.slv.service.infrastructure.doris.dto.CanNavListQueryDTO;
import dst.v2x.business.slv.service.infrastructure.doris.entity.CanData;
import dst.v2x.business.slv.service.infrastructure.doris.entity.CanDataNav;
import dst.v2x.business.slv.service.infrastructure.doris.service.CanDataNavServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * CAN数据-导航-相关服务
 *
 * @author: pengyunlin
 * @date: 2025/8/13 15:51
 */
@Service
@Slf4j
public class CanDataNavBusinessService {
    @Autowired
    private CanDataNavServiceImpl canDataNavService;

    /**
     * 根据年、月查询该月有哪些天
     */
    public List<String> queryOfMonth(CanNavListQueryDTO dto) {
        List<Integer> days = canDataNavService.queryOfMonth(dto);

        // 格式化返回
        return days.stream().map(d -> String.format("%02d", d)).collect(Collectors.toList());
    }

    /**
     * 根据年、月查询该月有哪些天
     */
    public List<String> queryOfDay(CanNavListQueryDTO dto) {
        List<Integer> days = canDataNavService.queryOfDay(dto);

        // 格式化返回
        return days.stream().map(d -> String.format("%02d", d)).collect(Collectors.toList());
    }

    /**
     * 批量插入
     * @param canDatas
     */
    public void insertBatch(List<CanData> canDatas){
        if(CollectionUtils.isEmpty(canDatas)){
            return;
        }
        canDataNavService.insertBatch(getCanNavDate(canDatas));
    }

    /**
     * 过滤出年月日
     *
     * @param canDatas
     * @return List<TblVehicleHistoryDate>
     * @author 江民来
     * @date 2024/11/16 11:21
     */
    private List<CanDataNav> getCanNavDate(List<CanData> canDatas) {
        Set<CanDataNav> collect = canDatas.stream().map(s -> {
            CanDataNav nav = new CanDataNav();
            nav.setVehicleNo(s.getVehicleNo());
            String dataTimeStr = DateUtil.format(s.getDataTime(), "yyyy-MM-dd HH:mm:ss");
            // 分割就拿到年月日和时分秒
            String[] split = dataTimeStr.split(" ");
            String dateYear = split[0];
            // 以'-'切割
            String[] split1 = dateYear.split("-");
            nav.setYear(DateUtil.parse(split1[0], "yyyy").toLocalDateTime().toLocalDate());
            nav.setMonth(Integer.valueOf(split1[1]));
            nav.setDay(Integer.valueOf(split1[2]));
            String datehour = split[1];
            // 冒号切割，就拿到了时分秒
            String[] split2 = datehour.split(":");
            nav.setHour(Integer.valueOf(split2[0]));
            return nav;
        }).collect(Collectors.toSet());
        return new ArrayList<>(collect);
    }
}

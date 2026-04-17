package dst.v2x.business.slv.service.module.business.raw.service;

import cn.hutool.core.date.DateUtil;
import dst.v2x.business.slv.service.infrastructure.doris.dto.RawNavListQueryDTO;
import dst.v2x.business.slv.service.infrastructure.doris.entity.RawData;
import dst.v2x.business.slv.service.infrastructure.doris.entity.RawDataNav;
import dst.v2x.business.slv.service.infrastructure.doris.service.RawDataNavServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 上报数据-导航-相关服务
 *
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 */
@Service
@Slf4j
public class RawDataNavBusinessService {
    @Autowired
    private RawDataNavServiceImpl rawDataNavService;

    /**
     * 根据年、月查询该月有哪些天
     *
     * @param dto
     * @return Response
     * @author 江民来
     * @date 2024/11/16 11:07
     */
    public List<String> queryOfMonth(RawNavListQueryDTO dto) {
        List<Integer> days = rawDataNavService.queryOfMonth(dto);

        // 格式化返回
        return days.stream().map(d -> String.format("%02d", d)).collect(Collectors.toList());
    }

    /**
     * 根据年、月查询该月有哪些天
     *
     * @param dto
     * @return Response
     * @author 江民来
     * @date 2024/11/16 11:07
     */
    public List<String> queryOfDay(RawNavListQueryDTO dto) {
        List<Integer> days = rawDataNavService.queryOfDay(dto);

        // 格式化返回
        return days.stream().map(d -> String.format("%02d", d)).collect(Collectors.toList());
    }

    /**
     * 批量插入
     * @param rawDatas
     */
    public void insertBatch(List<RawData> rawDatas){
        if(CollectionUtils.isEmpty(rawDatas)){
            return;
        }
        rawDataNavService.insertBatch(getRawNavDate(rawDatas));
    }

    /**
     * 过滤出年月日
     *
     * @param rawDatas
     * @return List<TblVehicleHistoryDate>
     * @author 江民来
     * @date 2024/11/16 11:21
     */
    private List<RawDataNav> getRawNavDate(List<RawData> rawDatas) {
        Set<RawDataNav> collect = rawDatas.stream().map(s -> {
            RawDataNav nav = new RawDataNav();
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

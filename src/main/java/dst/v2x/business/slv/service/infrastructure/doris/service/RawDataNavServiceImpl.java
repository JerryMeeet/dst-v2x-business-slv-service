package dst.v2x.business.slv.service.infrastructure.doris.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dst.v2x.business.slv.service.infrastructure.doris.dto.RawNavListQueryDTO;
import dst.v2x.business.slv.service.infrastructure.doris.entity.RawDataNav;
import dst.v2x.business.slv.service.infrastructure.doris.mapper.RawDataNavMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;

/**
* @author DST
* @description 针对表【slv_raw_data_nav】的数据库操作Service实现
* @createDate 2025-06-10 14:56:12
*/
@Service
public class RawDataNavServiceImpl extends ServiceImpl<RawDataNavMapper, RawDataNav> {
    @Autowired
    private RawDataNavMapper rawDataNavMapper;

    /**
     * 根据年、月查询该月有哪些天
     */
    public List<Integer> queryOfMonth(RawNavListQueryDTO queryDTO){
        LocalDate time = queryDTO.getTime();
        // 转回 LocalDate：设为该年的1月1日
        LocalDate year = LocalDate.of(time.getYear(), 1, 1);
        return rawDataNavMapper.queryOfMonth(queryDTO.getVehicleNo(), year, time.getMonthValue());
    }

    /**
     * 根据年、月、日查询该月有哪些时
     */
    public List<Integer> queryOfDay(RawNavListQueryDTO queryDTO){
        LocalDate time = queryDTO.getTime();
        // 转回 LocalDate：设为该年的1月1日
        LocalDate year = LocalDate.of(time.getYear(), 1, 1);
        return rawDataNavMapper.queryOfDay(queryDTO.getVehicleNo(), year, time.getMonthValue(), time.getDayOfMonth());
    }

    /**
     * 批量插入
     * @param list
     */
    public void insertBatch(List<RawDataNav> list){
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        rawDataNavMapper.insertBatch(list);
    }
}





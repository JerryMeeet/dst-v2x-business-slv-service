package dst.v2x.business.slv.service.infrastructure.doris.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dst.v2x.business.slv.service.infrastructure.doris.dto.CanNavListQueryDTO;
import dst.v2x.business.slv.service.infrastructure.doris.entity.CanDataNav;
import dst.v2x.business.slv.service.infrastructure.doris.mapper.CanDataNavMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;

/**
* @author DST
* @description 针对表【slv_can_data_nav】的数据库操作Service实现
* @createDate 2025-06-10 14:56:12
*/
@Service
public class CanDataNavServiceImpl extends ServiceImpl<CanDataNavMapper, CanDataNav> {
    @Autowired
    private CanDataNavMapper canDataNavMapper;

    /**
     * 根据年、月查询该月有哪些天
     */
    public List<Integer> queryOfMonth(CanNavListQueryDTO queryDTO){
        LocalDate time = queryDTO.getTime();
        // 转回 LocalDate：设为该年的1月1日
        LocalDate year = LocalDate.of(time.getYear(), 1, 1);
        return canDataNavMapper.queryOfMonth(queryDTO.getVehicleNo(), year, time.getMonthValue());
    }

    /**
     * 根据年、月、日查询该月有哪些时
     */
    public List<Integer> queryOfDay(CanNavListQueryDTO queryDTO){
        LocalDate time = queryDTO.getTime();
        // 转回 LocalDate：设为该年的1月1日
        LocalDate year = LocalDate.of(time.getYear(), 1, 1);
        return canDataNavMapper.queryOfDay(queryDTO.getVehicleNo(), year, time.getMonthValue(), time.getDayOfMonth());
    }

    /**
     * 批量插入
     * @param list
     */
    public void insertBatch(List<CanDataNav> list){
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        canDataNavMapper.insertBatch(list);
    }
}





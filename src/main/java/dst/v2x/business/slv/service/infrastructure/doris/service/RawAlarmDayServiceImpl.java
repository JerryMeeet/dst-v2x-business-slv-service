package dst.v2x.business.slv.service.infrastructure.doris.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dst.steed.vds.common.domain.response.PageDTO;
import dst.v2x.business.slv.service.common.enums.raw.RawAlarmTypeEnum;
import dst.v2x.business.slv.service.common.utils.DstPageUtil;
import dst.v2x.business.slv.service.infrastructure.doris.dto.RawAlarmPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.doris.entity.RawAlarmDay;
import dst.v2x.business.slv.service.infrastructure.doris.mapper.RawAlarmDayMapper;
import dst.v2x.business.slv.service.infrastructure.doris.vo.RawAlarmVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author DST
 * @description 针对表【slv_raw_alarm_day】的数据库操作Service实现
 * @createDate 2025-06-12 17:33:29
 */
@Service
public class RawAlarmDayServiceImpl extends ServiceImpl<RawAlarmDayMapper, RawAlarmDay> {
    @Autowired
    private RawAlarmDayMapper rawAlarmDayMapper;

    /**
     * 分页查询
     *
     * @param dto
     * @author 江民来
     * @date 2025/6/16 17:53
     */
    public PageDTO<RawAlarmVO> queryPage(RawAlarmPageQueryDTO dto) {
        IPage<RawAlarmVO> page = rawAlarmDayMapper.findPage(DstPageUtil.toPage(dto), dto);
        PageDTO<RawAlarmVO> pageDTO = DstPageUtil.toPageDTO(page);
        pageDTO.getList().forEach(s -> s.setDataTypeName(RawAlarmTypeEnum.getNameByCode(s.getDataType())));
        return pageDTO;
    }

    /**
     * 批量插入
     *
     * @author 江民来
     * @date 2025/6/13 14:28
     */
    public void insertBatch(List<RawAlarmDay> list) {
        rawAlarmDayMapper.insertBatch(list);
    }

}





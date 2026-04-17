package dst.v2x.business.slv.service.module.business.alarm.service;

import com.dst.steed.vds.common.domain.response.PageDTO;
import dst.v2x.business.slv.service.infrastructure.doris.dto.RawAlarmPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.doris.service.RawAlarmDayServiceImpl;
import dst.v2x.business.slv.service.infrastructure.doris.vo.RawAlarmVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 江民来
 * @date 2025年06月13日 18:07
 */
@Service
@Slf4j
public class AlarmDayService {
    @Autowired
    private RawAlarmDayServiceImpl rawAlarmDayService;

    /**
     * 分页查询
     *
     * @param dto
     * @author 江民来
     * @date 2025/6/16 17:52
     */
    public PageDTO<RawAlarmVO> queryPage(RawAlarmPageQueryDTO dto) {
        return rawAlarmDayService.queryPage(dto);
    }
}

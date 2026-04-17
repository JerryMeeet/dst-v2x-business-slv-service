package dst.v2x.business.slv.service.module.business.alarm.service;

import dst.v2x.business.slv.service.common.enums.raw.RawAlarmTypeEnum;
import dst.v2x.business.slv.service.infrastructure.doris.dto.RawAlarmListQueryDTO;
import dst.v2x.business.slv.service.infrastructure.doris.service.RawAlarmHistoryServiceImpl;
import dst.v2x.business.slv.service.infrastructure.doris.vo.RawAlarmVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 江民来
 * @date 2025年06月13日 18:07
 */
@Service
@Slf4j
public class AlarmHistoryService {
    @Autowired
    private RawAlarmHistoryServiceImpl rawAlarmHistoryService;

    /**
     * 分页查询
     *
     * @param dto
     * @author 江民来
     * @date 2025/6/16 16:04
     */
    public List<RawAlarmVO> queryList(RawAlarmListQueryDTO dto) {
        List<RawAlarmVO> voList = rawAlarmHistoryService.queryList(dto);
        voList.forEach(s -> s.setDataTypeName(RawAlarmTypeEnum.getNameByCode(s.getDataType())));
        return voList;
    }
}

package dst.v2x.business.slv.service.infrastructure.biz.archive.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.TaskInfoPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.TaskInfo;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.TaskInfo;
import dst.v2x.business.slv.service.infrastructure.biz.archive.vo.TaskInfoPageQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author DST
* @description 针对表【slv_task_info(任务信息表)】的数据库操作Mapper
* @createDate 2025-07-03 11:13:50
* @Entity dst.v2x.business.slv.service.infrastructure.biz.archive.entity.TaskInfo
*/
public interface TaskInfoMapper extends BaseMapper<TaskInfo> {
    /**
     * 批量新增
     */
    int batchAdd(@Param("list") List<TaskInfo> list);

    /**
     * 分页查询
     *
     * @param page
     * @param query
     * @return
     */
    IPage<TaskInfoPageQueryVO> findPage(IPage<TaskInfo> page, @Param("query") TaskInfoPageQueryDTO query);
}





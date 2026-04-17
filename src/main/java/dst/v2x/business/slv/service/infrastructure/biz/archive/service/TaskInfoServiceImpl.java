package dst.v2x.business.slv.service.infrastructure.biz.archive.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dst.steed.vds.common.domain.response.PageDTO;
import com.dst.steed.vds.common.util.DstJsonUtil;
import dst.v2x.business.slv.service.common.utils.DataCompareHelper;
import dst.v2x.business.slv.service.common.utils.DstPageUtil;
import dst.v2x.business.slv.service.common.utils.StatusDescUtil;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.TaskInfoPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.TaskInfo;
import dst.v2x.business.slv.service.infrastructure.biz.archive.mapper.TaskInfoMapper;
import dst.v2x.business.slv.service.infrastructure.biz.archive.vo.TaskInfoPageQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author DST
* @description 针对表【slv_task_info(任务信息表)】的数据库操作Service实现
* @createDate 2025-07-03 11:13:50
*/
@Service
@Slf4j
public class TaskInfoServiceImpl extends ServiceImpl<TaskInfoMapper, TaskInfo> {
    @Autowired
    private TaskInfoMapper taskInfoMapper;

    /**
     * 分页查询
     *
     * @author: pengyunlin
     * @date: 2025/6/6 15:51
     */
    public PageDTO<TaskInfoPageQueryVO> queryPage(TaskInfoPageQueryDTO queryDTO) {
        log.info("任务信息的分页查询的请参：{}", DstJsonUtil.toString(queryDTO));
        IPage<TaskInfoPageQueryVO> page = this.getBaseMapper().findPage(DstPageUtil.toPage(queryDTO), queryDTO);
        PageDTO<TaskInfoPageQueryVO> pageDTO = DstPageUtil.toPageDTO(page);

        //翻译状态值
        pageDTO.getList().forEach(vo-> StatusDescUtil.convertStatusDesc(vo));
        return pageDTO;
    }

    /**
     * 批量新增或更新
     */
    public void batchAddOrUpdate(List<TaskInfo> taskInfos, Integer belongCompany) {
        if(CollectionUtil.isEmpty(taskInfos)){
            return;
        }

        List<TaskInfo> addList = new ArrayList<>();
        List<TaskInfo> updateList = new ArrayList<>();

        //填充数据, 区分新增和更新
        fillData(taskInfos, addList, updateList, belongCompany);

        //批量新增
        if(CollectionUtil.isNotEmpty(addList)){
            taskInfoMapper.batchAdd(addList);
        }

        //批量修改
        if(CollectionUtil.isNotEmpty(updateList)){
            updateBatchById(updateList);
        }
    }

    /**
     * 填充数据,区分新增和更新
     */
    private void fillData(List<TaskInfo> newList,
                          List<TaskInfo> addList,
                          List<TaskInfo> updateList,
                          Integer belongCompany) {
        List<String> taskNos = newList.stream().map(TaskInfo::getTaskNo).collect(Collectors.toList());
        List<TaskInfo> oldList = getListByTaskNo(taskNos, belongCompany);

        //比较数据
        DataCompareHelper.CompareResult<TaskInfo> compare = DataCompareHelper.<TaskInfo>builder()
                .newDataSupplier(() -> newList)
                .oldDataSupplier(() -> oldList)
                .uniqueKeyFunction(TaskInfo::getTaskNo)
                .addFunction((n) -> {
                    n.setId(IdWorker.getId());
                    n.setBelongCompany(belongCompany);
                    return n;
                })
                .updateFunction((n, o) -> {
                    n.setId(o.getId());
                    n.setCreator(null);
                    n.setCreateTime(null);
                    n.setCreatorName(null);
                    n.setBelongCompany(belongCompany);
                    return n;
                }).build().compare();

        //新增数据
        if (CollectionUtils.isNotEmpty(compare.getToAdd())) {
            addList.addAll(compare.getToAdd());
        }

        //修改数据
        if (CollectionUtils.isNotEmpty(compare.getToUpdate())) {
            updateList.addAll(compare.getToUpdate());
        }
    }

    /**
     * 根据基表站点编号集合查询列表
     */
    public List<TaskInfo> getListByTaskNo(List<String> taskNos, Integer belongCompany){
        if(CollectionUtil.isEmpty(taskNos)){
            return new ArrayList<>();
        }
        return super.baseMapper.selectList(new LambdaQueryWrapper<TaskInfo>()
                .in(TaskInfo::getTaskNo, taskNos)
                .eq(TaskInfo::getBelongCompany, belongCompany));
    }
}





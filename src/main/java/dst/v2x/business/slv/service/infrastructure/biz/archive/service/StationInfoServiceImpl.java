package dst.v2x.business.slv.service.infrastructure.biz.archive.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dst.steed.vds.common.domain.response.PageDTO;
import com.dst.steed.vds.common.util.DstJsonUtil;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.common.utils.DataCompareHelper;
import dst.v2x.business.slv.service.common.utils.DstPageUtil;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.StationInfoPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.StationInfo;
import dst.v2x.business.slv.service.infrastructure.biz.archive.mapper.StationInfoMapper;
import dst.v2x.business.slv.service.infrastructure.biz.archive.vo.StationInfoPageQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author DST
* @description 针对表【slv_station_info(站点信息表)】的数据库操作Service实现
* @createDate 2025-07-03 11:13:50
*/
@Slf4j
@Service
public class StationInfoServiceImpl extends ServiceImpl<StationInfoMapper, StationInfo> {
    @Autowired
    private StationInfoMapper stationInfoMapper;

    /**
     * 分页查询
     *
     * @author: pengyunlin
     * @date: 2025/6/6 15:51
     */
    public PageDTO<StationInfoPageQueryVO> queryPage(StationInfoPageQueryDTO queryDTO) {
        log.info("站点信息的分页查询的请参：{}", DstJsonUtil.toString(queryDTO));
        IPage<StationInfoPageQueryVO> page = this.getBaseMapper().findPage(DstPageUtil.toPage(queryDTO), queryDTO);
        PageDTO<StationInfoPageQueryVO> pageDTO = DstPageUtil.toPageDTO(page);

        // 转换归属公司
        List<StationInfoPageQueryVO> pageVOList = pageDTO.getList();
        pageVOList.forEach(pageVO -> {
            if (Objects.nonNull(pageVO.getBelongCompany())) {
                pageVO.setBelongCompanyName(VehicleCompanyEnum.getDescByCode(pageVO.getBelongCompany()));
            }
        });
        pageDTO.setList(pageVOList);
        return pageDTO;
    }

    /**
     * 批量新增或更新
     */
    public void batchAddOrUpdate(List<StationInfo> stationInfos, Integer belongCompany) {
        if(CollectionUtil.isEmpty(stationInfos)){
            return;
        }

        List<StationInfo> addList = new ArrayList<>();
        List<StationInfo> updateList = new ArrayList<>();

        //填充数据, 区分新增和更新
        fillData(stationInfos, addList, updateList, belongCompany);

        //批量新增
        if(CollectionUtil.isNotEmpty(addList)){
            stationInfoMapper.batchAdd(addList);
        }

        //批量修改
        if(CollectionUtil.isNotEmpty(updateList)){
            updateBatchById(updateList);
        }
    }

    /**
     * 填充数据,区分新增和更新
     */
    private void fillData(List<StationInfo> newList,
                          List<StationInfo> addList,
                          List<StationInfo> updateList,
                          Integer belongCompany) {
        List<String> stationNos = newList.stream().map(StationInfo::getStationNo).collect(Collectors.toList());
        List<StationInfo> oldList = getListByStationNo(stationNos, belongCompany);

        //比较数据
        DataCompareHelper.CompareResult<StationInfo> compare = DataCompareHelper.<StationInfo>builder()
                .newDataSupplier(() -> newList)
                .oldDataSupplier(() -> oldList)
                .uniqueKeyFunction(StationInfo::getStationNo)
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
    public List<StationInfo> getListByStationNo(List<String> stationNos, Integer belongCompany){
        if(CollectionUtil.isEmpty(stationNos)){
            return new ArrayList<>();
        }
        return super.baseMapper.selectList(new LambdaQueryWrapper<StationInfo>()
                .in(StationInfo::getStationNo, stationNos)
                .eq(StationInfo::getBelongCompany, belongCompany));
    }

    /**
     * 根据基表站点编号集合查询MAP
     */
    public Map<String, StationInfo> getMapByStationNo(List<String> stationNos, Integer belongCompany){
        return getListByStationNo(stationNos, belongCompany)
                .stream()
                .collect(Collectors.toMap(StationInfo::getStationNo, v->v, (o1, o2) -> o1));
    }
}





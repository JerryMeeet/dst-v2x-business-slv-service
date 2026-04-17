package dst.v2x.business.slv.service.infrastructure.biz.archive.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dst.steed.vds.common.domain.response.PageDTO;
import com.dst.steed.vds.common.util.DstJsonUtil;
import dst.v2x.business.slv.service.common.utils.DataCompareHelper;
import dst.v2x.business.slv.service.common.utils.DstPageUtil;
import dst.v2x.business.slv.service.common.utils.StatusDescUtil;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.MapMakePageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.MapMake;
import dst.v2x.business.slv.service.infrastructure.biz.archive.mapper.MapMakeMapper;
import dst.v2x.business.slv.service.infrastructure.biz.archive.vo.MapMakePageQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author DST
* @description 针对表【slv_map_make(路线表)】的数据库操作Service实现
* @createDate 2025-07-03 11:13:50
*/
@Slf4j
@Service
public class MapMakeServiceImpl extends ServiceImpl<MapMakeMapper, MapMake> {
    @Autowired
    private MapMakeMapper mapMakeMapper;

    /**
     * 分页查询
     *
     * @author: pengyunlin
     * @date: 2025/6/6 15:51
     */
    public PageDTO<MapMakePageQueryVO> queryPage(MapMakePageQueryDTO queryDTO) {
        log.info("路线的分页查询的请参：{}", DstJsonUtil.toString(queryDTO));
        IPage<MapMakePageQueryVO> page = this.getBaseMapper().findPage(DstPageUtil.toPage(queryDTO), queryDTO);
        PageDTO<MapMakePageQueryVO> pageDTO = DstPageUtil.toPageDTO(page);

        //翻译状态值
        pageDTO.getList().forEach(vo-> StatusDescUtil.convertStatusDesc(vo));
        return pageDTO;
    }

    /**
     * 批量新增或更新
     */
    public void batchAddOrUpdate(List<MapMake> mapMakes, Integer belongCompany) {
        if(CollectionUtil.isEmpty(mapMakes)){
            return;
        }

        List<MapMake> addList = new ArrayList<>();
        List<MapMake> updateList = new ArrayList<>();

        //填充数据, 区分新增和更新
        fillData(mapMakes, addList, updateList, belongCompany);

        //批量新增
        if(CollectionUtil.isNotEmpty(addList)){
            mapMakeMapper.batchAdd(addList);
        }

        //批量修改
        if(CollectionUtil.isNotEmpty(updateList)){
            updateBatchById(updateList);
        }
    }

    /**
     * 填充数据,区分新增和更新
     */
    private void fillData(List<MapMake> newList,
                          List<MapMake> addList,
                          List<MapMake> updateList,
                          Integer belongCompany) {
        List<Integer> mapMakeIds = newList.stream().map(MapMake::getMapMakeId).collect(Collectors.toList());
        List<MapMake> oldList = getListByMapMakeId(mapMakeIds, belongCompany);

        //比较数据
        DataCompareHelper.CompareResult<MapMake> compare = DataCompareHelper.<MapMake>builder()
                .newDataSupplier(() -> newList)
                .oldDataSupplier(() -> oldList)
                .uniqueKeyFunction(MapMake::getMapMakeId)
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
     * 根据基表路线ID集合查询列表
     */
    public List<MapMake> getListByMapMakeId(List<Integer> mapMakeIds, Integer belongCompany){
        if(CollectionUtil.isEmpty(mapMakeIds)){
            return new ArrayList<>();
        }
        return super.baseMapper.selectList(new LambdaQueryWrapper<MapMake>()
                .in(MapMake::getMapMakeId, mapMakeIds)
                .eq(MapMake::getBelongCompany, belongCompany));
    }
}





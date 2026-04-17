package dst.v2x.business.slv.service.infrastructure.biz.archive.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dst.steed.vds.common.domain.response.PageDTO;
import com.dst.steed.vds.common.util.DstJsonUtil;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.common.utils.DataCompareHelper;
import dst.v2x.business.slv.service.common.utils.DstPageUtil;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.VehicleInfoPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.VehicleInfo;
import dst.v2x.business.slv.service.infrastructure.biz.archive.mapper.VehicleInfoMapper;
import dst.v2x.business.slv.service.infrastructure.biz.archive.vo.VehicleInfoPageQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
* @author pengyunlin
* @description 针对表【slv_vehicle_info(车辆信息表)】的数据库操作Service实现
* @createDate 2025-06-06 10:40:40
*/
@Service
@Slf4j
public class VehicleInfoServiceImpl extends ServiceImpl<VehicleInfoMapper, VehicleInfo> {
    @Autowired
    private VehicleInfoMapper vehicleInfoMapper;

    /**
     * 查询单条-VO
     */
    public VehicleInfoPageQueryVO queryOneVO(String vehicleNo) {
        VehicleInfo vehicleInfo = baseMapper.selectOne(Wrappers.<VehicleInfo>lambdaQuery()
                .eq(VehicleInfo::getVehicleNo, vehicleNo)
                .last("LIMIT 1"));
        VehicleInfoPageQueryVO vo = new VehicleInfoPageQueryVO();
        BeanUtil.copyProperties(vehicleInfo, vo);
        vo.setBelongCompanyName(VehicleCompanyEnum.getDescByCode(vo.getBelongCompany()));
        return vo;
    }

    /**
     * 查询单条
     */
    public VehicleInfo queryOne(String vehicleNo) {
        return baseMapper.selectOne(Wrappers.<VehicleInfo>lambdaQuery()
                .eq(VehicleInfo::getVehicleNo, vehicleNo)
                .last("LIMIT 1"));
    }

    /**
     * 分页查询
     *
     * @author: pengyunlin
     * @date: 2025/6/6 15:51
     */
    public PageDTO<VehicleInfoPageQueryVO> queryPage(VehicleInfoPageQueryDTO queryDTO) {
        log.info("车辆信息的分页查询的请参：{}", DstJsonUtil.toString(queryDTO));
        IPage<VehicleInfoPageQueryVO> page = this.getBaseMapper().findPage(DstPageUtil.toPage(queryDTO), queryDTO);
        PageDTO<VehicleInfoPageQueryVO> pageDTO = DstPageUtil.toPageDTO(page);

        // 转换归属公司
        List<VehicleInfoPageQueryVO> pageVOList = pageDTO.getList();
        pageVOList.forEach(pageVO -> {
            if (Objects.nonNull(pageVO.getBelongCompany())) {
                pageVO.setBelongCompanyName(VehicleCompanyEnum.getDescByCode(pageVO.getBelongCompany()));
            }
        });
        pageDTO.setList(pageVOList);
        return pageDTO;
    }

    /**
     * 获取车辆型号列表
     * @return
     */
    public List<String> queryTypeList() {
        log.info(">>>车辆型号列表查询");
        LambdaQueryWrapper<VehicleInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(VehicleInfo::getVehicleType).groupBy(VehicleInfo::getVehicleType)
                .isNotNull(VehicleInfo::getVehicleType);
        return list(queryWrapper).stream().map(VehicleInfo::getVehicleType).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 分页查询-车辆编码
     *
     * @author: pengyunlin
     * @date: 2025/6/6 15:51
     */
    public PageDTO<String> queryNoPage(VehicleInfoPageQueryDTO queryDTO) {
        log.debug("车辆编码的分页查询的请参：{}", DstJsonUtil.toString(queryDTO));
        IPage<String> page = this.getBaseMapper().findNoPage(DstPageUtil.toPage(queryDTO), queryDTO);
        return DstPageUtil.toPageDTO(page);
    }

    /**
     * 分页查询-车辆VIN
     *
     * @author: pengyunlin
     * @date: 2025/6/6 15:51
     */
    public PageDTO<String> queryVinPage(VehicleInfoPageQueryDTO queryDTO) {
        log.debug("车辆VIN的分页查询的请参：{}", DstJsonUtil.toString(queryDTO));
        IPage<String> page = this.getBaseMapper().findVinPage(DstPageUtil.toPage(queryDTO), queryDTO);
        return DstPageUtil.toPageDTO(page);
    }

    /**
     * 批量新增或更新
     * @author pengyunlin
     * @date: 2025/6/6 15:51
     */
    public void batchAddOrUpdate(List<VehicleInfo> vehicleInfos) {
        if(CollectionUtil.isEmpty(vehicleInfos)){
            return;
        }

        List<VehicleInfo> addList = new ArrayList<>();
        List<VehicleInfo> updateList = new ArrayList<>();

        //填充数据, 区分新增和更新
        fillData(vehicleInfos, addList, updateList);

        //批量新增
        if(CollectionUtil.isNotEmpty(addList)){
            vehicleInfoMapper.batchAdd(addList);
        }

        //批量修改
        if(CollectionUtil.isNotEmpty(updateList)){
            updateBatchById(updateList);
        }
    }

    /**
     * 批量删除
     */
    public void batchRemove(List<String> vehicleNos) {
        if(CollectionUtils.isEmpty(vehicleNos)){
            return;
        }
        LambdaUpdateWrapper<VehicleInfo> removeWrapper = new LambdaUpdateWrapper<>();
        removeWrapper.in(VehicleInfo::getVehicleNo, vehicleNos);
        this.remove(removeWrapper);
    }

    /**
     * 根据基表车辆编号集合查询列表
     */
    public List<VehicleInfo> getListByVehicleNo(List<String> vehicleNos){
        if(CollectionUtil.isEmpty(vehicleNos)){
            return new ArrayList<>();
        }
        return super.baseMapper.selectList(new LambdaQueryWrapper<VehicleInfo>()
                .in(VehicleInfo::getVehicleNo, vehicleNos));
    }

    /**
     * 根据基表车辆编号集合查询vin map
     */
    public Map<String, String> getVinMapByVehicleNo(List<String> vehicleNos){
        List<VehicleInfo> list = getListByVehicleNo(vehicleNos);
        if(CollectionUtils.isEmpty(list)){
            return new HashMap<>();
        }
        return list.stream().collect(Collectors.toMap(VehicleInfo::getVehicleNo, VehicleInfo::getVehicleVin, (o1, o2) -> o1));
    }

    /**
     * 填充数据
     */
    private void fillData(List<VehicleInfo> newList,
                                   List<VehicleInfo> addList,
                                   List<VehicleInfo> updateList) {
        List<String> vehicleNos = newList.stream().map(VehicleInfo::getVehicleNo).collect(Collectors.toList());
        List<VehicleInfo> oldList = getListByVehicleNo(vehicleNos);

        //比较数据
        DataCompareHelper.CompareResult<VehicleInfo> compare = DataCompareHelper.<VehicleInfo>builder()
                .newDataSupplier(() -> newList)
                .oldDataSupplier(() -> oldList)
                .uniqueKeyFunction(VehicleInfo::getVehicleNo)
                .addFunction((n) -> {
                    n.setId(IdWorker.getId());
                    return n;
                })
                .updateFunction((n, o) -> {
                    n.setId(o.getId());
                    n.setCreator(null);
                    n.setCreateTime(null);
                    n.setCreatorName(null);
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
     * 修改iotda设备ID
     */
    public void updateIotdaDeviceId(String iotdaDeviceId, String vehicleNo) {
        if(StringUtils.isBlank(iotdaDeviceId) || StringUtils.isBlank(vehicleNo)){
            return;
        }
        LambdaUpdateWrapper<VehicleInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(VehicleInfo::getIotdaDeviceId, iotdaDeviceId)
                .eq(VehicleInfo::getVehicleNo, vehicleNo);
        this.update(updateWrapper);
    }
}





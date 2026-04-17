package dst.v2x.business.slv.service.infrastructure.acl.zelos.service;

import cn.hutool.core.bean.BeanUtil;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.api.ZelosAutoCarFeignApi;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.convert.ZelosTimeConvert;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.request.ZelosCanReq;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.response.ZelosResponse2;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.response.can.ZelosCanRes;
import dst.v2x.business.slv.service.infrastructure.doris.entity.CanData;
import dst.v2x.business.slv.service.infrastructure.doris.entity.can.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 九识数据相关接口-Can
 */
@Slf4j
@Service
public class ZelosCanApiService {
    @Resource
    private ZelosAutoCarFeignApi zelosAutoCarFeignApi;

    /**
     * 根据车辆编号查询Can数据
     * @param vehicleNos
     * @return
     */
    public List<CanData> getZelosCanData(List<String> vehicleNos){
        ZelosCanReq req = new ZelosCanReq();
        req.setVehicleNoIds(vehicleNos);
        ZelosResponse2<List<ZelosCanRes>> response = zelosAutoCarFeignApi.getZelosCanData(req);
        if(response == null){
            return new ArrayList<>();
        }
        return response.getData().stream().map(d->convertZelosCanDataRes(d)).collect(Collectors.toList());
    }

    /**
     * 九识CAN数据转换为数据库对象
     */
    private CanData convertZelosCanDataRes(ZelosCanRes zelosCanRes) {
        CanData canData = new CanData();
        canData.setVehicleNo(zelosCanRes.getName());
        canData.setDataTime(ZelosTimeConvert.convertZelosTime(zelosCanRes.getEventTime()));
        canData.setPullTime(LocalDateTime.now());

        //abs数据
        CanAbsData abs = new CanAbsData();
        BeanUtil.copyProperties(zelosCanRes.getAbs(), abs);
        canData.setAbs(abs);

        //BMS数据
        CanBmsData bms = new CanBmsData();
        BeanUtil.copyProperties(zelosCanRes.getBms(), bms);
        canData.setBms(bms);

        //PDU高压配电系统
        CanPduData pdu = new CanPduData();
        BeanUtil.copyProperties(zelosCanRes.getPduOrHcm(), pdu);
        canData.setPdu(pdu);

        //MCU 动力系统
        CanMcuData mcu = new CanMcuData();
        BeanUtil.copyProperties(zelosCanRes.getMcu(), mcu);
        canData.setMcu(mcu);

        //VCU整车控制系统
        CanVcuData vcu = new CanVcuData();
        BeanUtil.copyProperties(zelosCanRes.getVcu(), vcu);
        canData.setVcu(vcu);

        //模块信息
        CanModuleData module = new CanModuleData();
        BeanUtil.copyProperties(zelosCanRes.getModule(), module);
        canData.setModule(module);

        //转向角度信号
        CanPowerSteeringData powerSteering = new CanPowerSteeringData();
        BeanUtil.copyProperties(zelosCanRes.getPowerSteering(), powerSteering);
        canData.setPowerSteering(powerSteering);
        return canData;
    }
}

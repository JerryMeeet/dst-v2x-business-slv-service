package dst.v2x.business.slv.service.infrastructure.acl.neolix.api;

import dst.v2x.business.slv.service.DstV2xBusinessSlvApplication;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.request.*;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.response.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author pengyl
 * @date 2025/6/16
 * @description 新石器无人车 接口请求
 */
@SpringBootTest(classes = DstV2xBusinessSlvApplication.class)
@Slf4j
public class NeolixRemoteApiTest {
    @Autowired
    private NeolixRemoteApi neolixRemoteApi;

    @Test
    public void getVehicleList() {
        NeolixVehicleBaseListReq req = new NeolixVehicleBaseListReq();
        req.setEnterpriseCode("DST0021");
        NeolixResponse<List<NeolixVehicleBaseListRes>> vehicleList = neolixRemoteApi.getVehicleBaseList(req);
        log.info("vehicleList: {}", vehicleList);
    }

    /**
     * 获取站点信息
     */
    @Test
    public void getStationList(){
        NeolixStationInfoReq req = new NeolixStationInfoReq();
        req.setVin("LHTBY2B28PY8EA008");
        NeolixResponse<List<NeolixStationInfoRes>> response = neolixRemoteApi.getStationList(req);
        log.info("response: {}", response);
    }

    /**
     * 获取任务信息
     */
    @Test
    public void getTaskList(){
        NeolixTaskInfoReq req = new NeolixTaskInfoReq();
        req.setVin("LHTBY2B28PY8EA008");
        NeolixResponse<List<NeolixTaskInfoRes>> response = neolixRemoteApi.getTaskList(req);
        log.info("response: {}", response);
    }

    /**
     * 新增任务信息
     */
    @Test
    public void vehicleStart(){
        NeolixTaskInfoAddReq req = new NeolixTaskInfoAddReq();
        req.setVin("LHTBY2B28PY8EA008");
        req.setDepartureStationId("315099");
        req.setDestinationStationId("285153");
        req.setBusiness("DST");
        NeolixResponse<NeolixTaskInfoAddRes> response = neolixRemoteApi.vehicleStart(req);
        log.info("response: {}", response);
    }

    /**
     * 查询当前任务信息
     */
    @Test
    public void getCurrentMission(){
        NeolixTaskInfoReq req = new NeolixTaskInfoReq();
        req.setVin("LHTBY2B28PY8EA008");
        NeolixResponse<NeolixTaskInfoCurrentRes> response = neolixRemoteApi.getCurrentMission(req);
        log.info("response: {}", response);
    }

    /**
     * 取消任务
     */
    @Test
    public void vehicleCancel(){
        /*NeolixTaskInfoReq req = new NeolixTaskInfoReq();
        req.setVin("LHTBY2B28PY8EA008");*/
        NeolixResponse<?> response = neolixRemoteApi.vehicleCancel("LHTBY2B28PY8EA008");
        log.info("response: {}", response);
    }

    /**
     * 开关机
     */
    @Test
    public void powerOnOff(){
        NeolixPowerOnOffReq req = new NeolixPowerOnOffReq();
        req.setVin("LHTBY2B28PY8EA008");
        req.setHandleType(1);
        NeolixResponse<?> response = neolixRemoteApi.powerOnOff(req);
        log.info("response: {}", response);
    }

    /**
     * 打开货箱
     */
    @Test
    public void cabinetOpen(){
        NeolixTaskInfoReq req = new NeolixTaskInfoReq();
        req.setVin("LHTBY2B28PY8EA008");
        req.setCabinetCode("W");
        NeolixResponse<?> response = neolixRemoteApi.cabinetOpen(req);
        log.info("response: {}", response);
    }

    /**
     * 获取车辆实时数据
     */
    @Test
    public void batchGetVehicleList(){
        NeolixResponse<List<NeolixVehicleInfoRes>> response = neolixRemoteApi.batchGetVehicleList(List.of("LHTBY2B28PY8EA008"));
        log.info("response: {}", response);
    }
}

package dst.v2x.business.slv.service.common.enums.raw;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/*
 * 九识无人车车辆业务状态
 * @author 江民来
 * @date 2025/2/12 18:04
 */
@Getter
@AllArgsConstructor
@ToString
public enum ZelosTravelStatusEnum {
    IDLE_STATUS(1, "IDLE", "空闲，车辆没有任务"),
    WITHORDERS_STATUS(2, "WITHORDERS", "装货，车辆派单或装单"),
    ONWAY_STATUS(3, "ONWAY", "去程，车辆自动驾驶中"),
    ATSTOP_STATUS(4, "ATSTOP", "投递，车辆处于取货点，等待用户"),
    BACK_STATUS(5, "BACK", "回站，车辆在返回装货点途中"),
    ARRIVEHOME_STATUS(6, "ARRIVEHOME", "到站，车辆到达了装货点，且车上有用户未取的订单。若所有货物都被取，不存在此状态"),
    ROUTING_STATUS(7, "ROUTING", "规划中，车辆在进行路径规划过程中。下一个状态是去程");


    private final int code; // 自定义的枚举值
    private final String type; // 九识定义的状态码
    private final String desc; // 九识定义的状态说明
}

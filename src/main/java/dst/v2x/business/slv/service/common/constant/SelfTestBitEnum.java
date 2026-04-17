package dst.v2x.business.slv.service.common.constant;

import lombok.AllArgsConstructor;

/**
 * 设备自检状态位
 */
@AllArgsConstructor
public enum SelfTestBitEnum {
    ADAS_STATUS(0, "ADAS摄像头连接状态：0未连接、1已连接"),
    DMS_STATUS(1, "DMS摄像头连接状态：0未连接、1已连接"),
    GPS_STATUS(2, "GPS信息状态：0无、1有"),
    RVC_STATUS(3,"倒车摄像头连接状态：0未连接、1已连接"),
    WSC_STATUS(4,"货舱摄像头连接状态：0未连接、1已连接"),
    ADAS_VIDEO_STATUS(8,"ADAS录像状态：0 录像异常、1 录像正常"),
    DMS_VIDEO_STATUS(9,"DMS录像状态：0 录像异常、1 录像正常"),
    RVC_VIDEO_STATUS(10,"倒车摄像头录像状态：0 录像异常、1 录像正常"),
    WSC_VIDEO_STATUS(11,"货舱摄像头录像状态：0 录像异常、1 录像正常"),
    ADAS_ANGLE_STATUS(12,"ADAS相机架设状态：0：角度异常、1 角度正常"),
    DMS_ANGLE_STATUS(13,"DMS相机架设状态：0：角度异常、1 角度正常"),
    RVC_ANGLE_STATUS(14,"RVC相机架设状态：0：角度异常、1 角度正常"),
    ADAS_MARK_STATUS(15,"ADAS标定状态：0：未标定、1：已标定"),
    DMS_MARK_STATUS(16,"DMS标定状态：0：未标定、1：已标定"),
    RVC_MARK_STATUS(17,"RVC标定状态：0：未标定、1：已标定"),

    SD_READ_WRITE_STATUS(0, "SD卡读写状态：0异常、1正常"),
    SD_STORAGE_STATUS(1, "SD卡初始化状态：0异常、1正常"),
    SD_INSERT_STATUS(2, "SD卡插入状态：0未插入、1已插入"),
    ;
    /**
     * 二进制状态位置
     */
    private final int bit;
    /**
     * 描述
     */
    private final String desc;

    public int getValue(int flag) {
        return flag >> this.bit & 0x01;
    }

}
package com.ruoyi.haikang.enums;

import com.ruoyi.haikang.net.HCNetSDK;

public enum HCPlayControlEnum {
    ZOOM_IN(HCNetSDK.ZOOM_IN, "焦距变大", "zoom_in"),
    ZOOM_OUT(HCNetSDK.ZOOM_OUT, "焦距变小", "zoom_out"),
    FOCUS_NEAR(HCNetSDK.FOCUS_NEAR, "焦点前调", "focus_near"),
    FOCUS_FAR(HCNetSDK.FOCUS_FAR, "焦点后调", "focus_far"),
    IRIS_OPEN(HCNetSDK.IRIS_OPEN, "光圈扩大", "iris_open"),
    IRIS_CLOSE(HCNetSDK.IRIS_CLOSE, "光圈缩小", "iris_close"),
    TILT_UP(HCNetSDK.TILT_UP, "云台上仰", "tilt_up"),
    TILT_DOWN(HCNetSDK.TILT_DOWN, "云台下俯", "tilt_down"),
    PAN_LEFT(HCNetSDK.PAN_LEFT, "云台左转", "pan_left"),
    PAN_RIGHT(HCNetSDK.PAN_RIGHT, "云台右转", "pan_right"),
    UP_LEFT(HCNetSDK.UP_LEFT, "云台上仰和左转", "up_left"),
    UP_RIGHT(HCNetSDK.UP_RIGHT, "云台上仰和右转", "up_right"),
    DOWN_LEFT(HCNetSDK.DOWN_LEFT, "云台下俯和左转", "down_left"),
    DOWN_RIGHT(HCNetSDK.DOWN_RIGHT, "云台下俯和右转", "down_right"),
    LIGHT_PWRON(HCNetSDK.LIGHT_PWRON, "接通灯光电源", "light_pwron"),
    WIPER_PWRON(HCNetSDK.WIPER_PWRON, "接通雨刷开关", "wiper_pwron"),
    FAN_PWRON(HCNetSDK.FAN_PWRON, "接通风扇开关", "fan_pwron"),
    HEATER_PWRON(HCNetSDK.HEATER_PWRON, "接通加热器开关", "heater_pwron"),
    AUX_PWRON1(HCNetSDK.AUX_PWRON1, "接通辅助设备开关1", "aux_pwron1"),
    AUX_PWRON2(HCNetSDK.AUX_PWRON2, "接通辅助设备开关2", "aux_pwron2"),
    PAN_AUTO(HCNetSDK.PAN_AUTO, "云台左右自动扫描", "pan_auto"),
    FILL_PRE_SEQ(HCNetSDK.FILL_PRE_SEQ, "将预置点加入巡航序列", "fill_pre_seq"),
    SET_SEQ_DWELL(HCNetSDK.SET_SEQ_DWELL, "设置巡航点停顿时间", "set_seq_dwell"),
    SET_SEQ_SPEED(HCNetSDK.SET_SEQ_SPEED, "设置巡航速度", "set_seq_speed"),
    CLE_PRE_SEQ(HCNetSDK.CLE_PRE_SEQ, "将预置点从巡航序列中删除", "cle_pre_seq"),
    STA_MEM_CRUISE(HCNetSDK.STA_MEM_CRUISE, "开始记录", "sta_mem_cruise"),
    STO_MEM_CRUISE(HCNetSDK.STO_MEM_CRUISE, "停止记录", "sto_mem_cruise"),
    RUN_CRUISE(HCNetSDK.RUN_CRUISE, "开始巡航", "run_cruise"),
    RUN_SEQ(HCNetSDK.RUN_SEQ, "开始巡航", "run_seq"),
    STOP_SEQ(HCNetSDK.STOP_SEQ, "停止巡航", "stop_seq");

    private Integer code;
    private String msg;
    private String value;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getValue() {
        return value;
    }

    HCPlayControlEnum(Integer code, String msg, String value) {
        this.code = code;
        this.msg = msg;
        this.value = value;
    }

    public static HCPlayControlEnum fromValue(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        for (HCPlayControlEnum item : HCPlayControlEnum.values()) {
            if (item.value.equalsIgnoreCase(value)) {
                return item;
            }
        }
        return null;
    }
}

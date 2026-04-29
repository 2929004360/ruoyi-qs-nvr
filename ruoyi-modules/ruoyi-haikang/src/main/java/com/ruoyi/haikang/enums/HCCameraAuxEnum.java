package com.ruoyi.haikang.enums;

import com.ruoyi.haikang.net.HCNetSDK;

public enum HCCameraAuxEnum {
    LIGHT_PWRON(HCNetSDK.LIGHT_PWRON, "接通灯光电源", "light_pwron"),
    WIPER_PWRON(HCNetSDK.WIPER_PWRON, "接通雨刷开关", "wiper_pwron"),
    FAN_PWRON(HCNetSDK.FAN_PWRON, "接通风扇开关", "fan_pwron"),
    HEATER_PWRON(HCNetSDK.HEATER_PWRON, "接通加热器开关", "heater_pwron"),
    AUX_PWRON1(HCNetSDK.AUX_PWRON1, "接通辅助设备开关1", "aux_pwron1"),
    AUX_PWRON2(HCNetSDK.AUX_PWRON2, "接通辅助设备开关2", "aux_pwron2");

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

    HCCameraAuxEnum(Integer code, String msg, String value) {
        this.code = code;
        this.msg = msg;
        this.value = value;
    }

    public static HCCameraAuxEnum fromValue(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        for (HCCameraAuxEnum item : HCCameraAuxEnum.values()) {
            if (item.value.equalsIgnoreCase(value)) {
                return item;
            }
        }
        return null;
    }
}

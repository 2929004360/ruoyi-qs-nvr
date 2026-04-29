package com.ruoyi.haikang.isup.enums;

/**
 * 海康ISUP辅助设备控制枚举（灯光、雨刮、风扇等）
 *
 * @FileName HCIsupCameraAuxEnum
 * @Description
 * @Author fengcheng
 * @date 2026-04-29
 **/
public enum HCIsupCameraAuxEnum {
    /**
     * 灯光
     */
    LIGHT(10, "灯光", "light"),
    /**
     * 雨刮
     */
    WIPER(14, "雨刮", "wiper"),
    /**
     * 风扇
     */
    FAN(11, "风扇", "fan"),
    /**
     * 加热器
     */
    HEATER(12, "加热器", "heater"),
    /**
     * 辅助设备1
     */
    AUX1(15, "辅助设备1", "aux1"),
    /**
     * 辅助设备2
     */
    AUX2(16, "辅助设备2", "aux2");

    private final int code;
    private final String desc;
    private final String value;

    HCIsupCameraAuxEnum(int code, String desc, String value) {
        this.code = code;
        this.desc = desc;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getValue() {
        return value;
    }

    /**
     * 根据value获取枚举
     *
     * @param value
     * @return
     */
    public static HCIsupCameraAuxEnum fromValue(String value) {
        for (HCIsupCameraAuxEnum enumValue : HCIsupCameraAuxEnum.values()) {
            if (enumValue.getValue().equals(value)) {
                return enumValue;
            }
        }
        return null;
    }
}

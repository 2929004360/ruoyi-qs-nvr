package com.ruoyi.haikang.isup.enums;

/**
 * 海康ISUP巡航控制枚举
 *
 * @FileName HCIsupCruiseControlEnum
 * @Description
 * @Author fengcheng
 * @date 2026-04-29
 **/
public enum HCIsupCruiseControlEnum {
    /**
     * 加入巡航序列
     */
    ADD_TO_CRUISE(17, "加入巡航序列", "add_to_cruise"),
    /**
     * 从巡航序列删除
     */
    REMOVE_FROM_CRUISE(18, "从巡航序列删除", "remove_from_cruise"),
    /**
     * 设置巡航停顿时间
     */
    SET_CRUISE_DWELL(19, "设置巡航停顿时间", "set_cruise_dwell"),
    /**
     * 设置巡航速度
     */
    SET_CRUISE_SPEED(20, "设置巡航速度", "set_cruise_speed"),
    /**
     * 开始巡航
     */
    START_CRUISE(21, "开始巡航", "start_cruise"),
    /**
     * 停止巡航
     */
    STOP_CRUISE(22, "停止巡航", "stop_cruise"),
    /**
     * 开始自动扫描
     */
    START_AUTO_SCAN(23, "开始自动扫描", "start_auto_scan"),
    /**
     * 停止自动扫描
     */
    STOP_AUTO_SCAN(24, "停止自动扫描", "stop_auto_scan");

    private final int code;
    private final String desc;
    private final String value;

    HCIsupCruiseControlEnum(int code, String desc, String value) {
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
    public static HCIsupCruiseControlEnum fromValue(String value) {
        for (HCIsupCruiseControlEnum enumValue : HCIsupCruiseControlEnum.values()) {
            if (enumValue.getValue().equals(value)) {
                return enumValue;
            }
        }
        return null;
    }
}

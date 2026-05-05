package com.ruoyi.haikang.isup.enums;

/**
 * 海康ISUP云台预置点控制枚举
 *
 * @FileName HCIsupPresetControlEnum
 * @Description
 * @Author fengcheng
 * @date 2026-04-29
 **/
public enum HCIsupPresetControlEnum {
    /**
     * 设置预置点
     */
    SET_PRESET(1, "设置预置点", "set_preset"),
    /**
     * 删除预置点
     */
    CLEAR_PRESET(2, "删除预置点", "clear_preset"),
    /**
     * 调用预置点
     */
    GOTO_PRESET(3, "调用预置点", "goto_preset");

    private final int code;
    private final String desc;
    private final String value;

    HCIsupPresetControlEnum(int code, String desc, String value) {
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
    public static HCIsupPresetControlEnum fromValue(String value) {
        for (HCIsupPresetControlEnum enumValue : HCIsupPresetControlEnum.values()) {
            if (enumValue.getValue().equals(value)) {
                return enumValue;
            }
        }
        return null;
    }
}

package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 421657
 * @description 支持灯光配置的版本
 * @origin autoTool
 * @date 2023/10/19 17:27:48
 */
public enum EM_LC_CONFIG_VERSION {
    /**
     * 未知
     */
    EM_LC_CONFIG_VERSION_UNKNOWN(0, "未知"),
    /**
     * 使用Lighting配置，默认
     */
    EM_LC_CONFIG_VERSION_LIGHTING(1, "使用Lighting配置，默认"),
    /**
     * 使用Lighting_V2配置
     */
    EM_LC_CONFIG_VERSION_LIGHTING_V2(2, "使用Lighting_V2配置");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_LC_CONFIG_VERSION(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_LC_CONFIG_VERSION enumType : EM_LC_CONFIG_VERSION.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_LC_CONFIG_VERSION enumType : EM_LC_CONFIG_VERSION.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_LC_CONFIG_VERSION getEnum(int value) {
        for (EM_LC_CONFIG_VERSION e : EM_LC_CONFIG_VERSION.values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return EM_LC_CONFIG_VERSION.EM_LC_CONFIG_VERSION_UNKNOWN;
    }
}


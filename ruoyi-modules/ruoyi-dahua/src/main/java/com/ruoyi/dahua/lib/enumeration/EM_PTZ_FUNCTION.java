package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 421657
 * @description 云台功能
 * @origin autoTool
 * @date 2023/10/16 19:23:11
 */
public enum EM_PTZ_FUNCTION {
    /**
     * 未知
     */
    NET_EM_PTZ_FUNCTION_UNKNOWN(0, "未知"),
    /**
     * None
     */
    NET_EM_PTZ_FUNCTION_NONE(1, "None"),
    /**
     * Scan功能
     */
    NET_EM_PTZ_FUNCTION_SCAN(2, "Scan功能"),
    /**
     * Pattern功能
     */
    NET_EM_PTZ_FUNCTION_PATTERN(3, "Pattern功能"),
    /**
     * Tour功能
     */
    NET_EM_PTZ_FUNCTION_TOUR(4, "Tour功能"),
    /**
     * AutoPan功能
     */
    NET_EM_PTZ_FUNCTION_AUTOPAN(5, "AutoPan功能"),
    /**
     * Preset功能
     */
    NET_EM_PTZ_FUNCTION_PRESET(6, "Preset功能"),
    /**
     * HeatMap功能
     */
    NET_EM_PTZ_FUNCTION_HEATMAP(7, "HeatMap功能"),
    /**
     * Patrol功能
     */
    NET_EM_PTZ_FUNCTION_PATROL(8, "Patrol功能");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_PTZ_FUNCTION(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_PTZ_FUNCTION enumType : EM_PTZ_FUNCTION.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_PTZ_FUNCTION enumType : EM_PTZ_FUNCTION.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_PTZ_FUNCTION getEnum(int value) {
        for (EM_PTZ_FUNCTION e : EM_PTZ_FUNCTION.values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return EM_PTZ_FUNCTION.NET_EM_PTZ_FUNCTION_UNKNOWN;
    }
}


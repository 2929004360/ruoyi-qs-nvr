package com.ruoyi.dahua.lib.enumeration;

/**
 * 设备运行状态
*/
public enum EM_SMART_MOTION_EQUIPMENT_MOTION_STATUS
{
    /**
     * 未知，初始状态
    */
    EM_SMART_MOTION_EQUIPMENT_MOTION_STATUS_UNKNOW(0, "未知，初始状态"),
    /**
     * 静止状态
    */
    EM_SMART_MOTION_EQUIPMENT_MOTION_STATUS_STATIC(1, "静止状态"),
    /**
     * 运动状态
    */
    EM_SMART_MOTION_EQUIPMENT_MOTION_STATUS_DYNAMIC(2, "运动状态");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_SMART_MOTION_EQUIPMENT_MOTION_STATUS(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_SMART_MOTION_EQUIPMENT_MOTION_STATUS enumType : EM_SMART_MOTION_EQUIPMENT_MOTION_STATUS.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_SMART_MOTION_EQUIPMENT_MOTION_STATUS enumType : EM_SMART_MOTION_EQUIPMENT_MOTION_STATUS.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}


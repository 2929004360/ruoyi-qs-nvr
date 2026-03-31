package com.ruoyi.dahua.lib.enumeration;

/**
 * 车位指示灯状态
*/
public enum EM_PARKINGSPACE_LIGHT_STATE
{
    /**
     * 未知
    */
    EM_PARKINGSPACE_LIGHT_STATE_UNKNOWN(-1, "未知"),
    /**
     * 灭
    */
    EM_PARKINGSPACE_LIGHT_STATE_OFF(0, "灭"),
    /**
     * 亮
    */
    EM_PARKINGSPACE_LIGHT_STATE_ON(1, "亮"),
    /**
     * 闪烁
    */
    EM_PARKINGSPACE_LIGHT_STATE_GLINT(2, "闪烁"),
    /**
     * 上一个状态
    */
    EM_PARKINGSPACE_LIGHT_STATE_LAST_STATE(3, "上一个状态"),
    /**
     * 两种颜色交替闪烁
    */
    EM_PARKINGSPACE_LIGHT_STATE_ALTERNATE_FLICKER(4, "两种颜色交替闪烁");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_PARKINGSPACE_LIGHT_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_PARKINGSPACE_LIGHT_STATE enumType : EM_PARKINGSPACE_LIGHT_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_PARKINGSPACE_LIGHT_STATE enumType : EM_PARKINGSPACE_LIGHT_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}


package com.ruoyi.dahua.lib.enumeration;

/**
 * 车位指示灯颜色
*/
public enum EM_PARKINGSPACE_LIGHT_COLOR
{
    /**
     * 未知
    */
    EM_PARKINGSPACE_LIGHT_COLOR_UNKNOWN(0, "未知"),
    /**
     * 红色
    */
    EM_PARKINGSPACE_LIGHT_COLOR_RED(1, "红色"),
    /**
     * 黄色
    */
    EM_PARKINGSPACE_LIGHT_COLOR_YELLOW(2, "黄色"),
    /**
     * 绿色
    */
    EM_PARKINGSPACE_LIGHT_COLOR_GREEN(3, "绿色"),
    /**
     * 蓝色
    */
    EM_PARKINGSPACE_LIGHT_COLOR_BLUE(4, "蓝色"),
    /**
     * 紫色
    */
    EM_PARKINGSPACE_LIGHT_COLOR_PURPLE(5, "紫色"),
    /**
     * 白色
    */
    EM_PARKINGSPACE_LIGHT_COLOR_WHITE(6, "白色"),
    /**
     * 粉色
    */
    EM_PARKINGSPACE_LIGHT_COLOR_PINK(7, "粉色"),
    /**
     * 青色
    */
    EM_PARKINGSPACE_LIGHT_COLOR_CYAN(8, "青色");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_PARKINGSPACE_LIGHT_COLOR(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_PARKINGSPACE_LIGHT_COLOR enumType : EM_PARKINGSPACE_LIGHT_COLOR.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_PARKINGSPACE_LIGHT_COLOR enumType : EM_PARKINGSPACE_LIGHT_COLOR.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}


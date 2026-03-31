package com.ruoyi.dahua.lib.enumeration;

/**
 * X光机外罩打开位置
*/
public enum EM_XRAY_SHELL_OPEN_POSITION
{
    /**
     * 未知
    */
    EM_XRAY_SHELL_OPEN_POSITION_UNKNWON(-1, "未知"),
    /**
     * 不区分位置
    */
    EM_XRAY_SHELL_OPEN_POSITION_INSENSITIVE(0, "不区分位置"),
    /**
     * A端左侧（面向A端通道时）
    */
    EM_XRAY_SHELL_OPEN_POSITION_A_LEFT(1, "A端左侧（面向A端通道时）"),
    /**
     * A端右侧（面向A端通道时）
    */
    EM_XRAY_SHELL_OPEN_POSITION_A_RIGHT(2, "A端右侧（面向A端通道时）"),
    /**
     * B端左侧（面向B端通道时）
    */
    EM_XRAY_SHELL_OPEN_POSITION_B_LEFT(3, "B端左侧（面向B端通道时）"),
    /**
     * B端右侧（面向B端通道时）
    */
    EM_XRAY_SHELL_OPEN_POSITION_B_RIGHT(4, "B端右侧（面向B端通道时）");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_XRAY_SHELL_OPEN_POSITION(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_XRAY_SHELL_OPEN_POSITION enumType : EM_XRAY_SHELL_OPEN_POSITION.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_XRAY_SHELL_OPEN_POSITION enumType : EM_XRAY_SHELL_OPEN_POSITION.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}


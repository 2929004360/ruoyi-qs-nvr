package com.ruoyi.dahua.lib.enumeration;

/**
 * 窗口轮巡动作
*/
public enum EM_NET_WINDOW_TOUR_ACTION
{
    /**
     * 开始
    */
    EM_NET_WND_TOUR_ACTION_START(0, "开始"),
    /**
     * 停止
    */
    EM_NET_WND_TOUR_ACTION_STOP(1, "停止");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_NET_WINDOW_TOUR_ACTION(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_NET_WINDOW_TOUR_ACTION enumType : EM_NET_WINDOW_TOUR_ACTION.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_NET_WINDOW_TOUR_ACTION enumType : EM_NET_WINDOW_TOUR_ACTION.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}


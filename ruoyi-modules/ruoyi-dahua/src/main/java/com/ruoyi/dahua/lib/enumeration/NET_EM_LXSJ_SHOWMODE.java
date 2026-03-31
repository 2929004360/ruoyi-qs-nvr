package com.ruoyi.dahua.lib.enumeration;


public enum NET_EM_LXSJ_SHOWMODE {
/**未知*/
    NET_EM_LXSJ_SHOWMODE_UNKNOWN(-1, "未知"),
/**立即显示*/
    NET_EM_LXSJ_SHOWMODE_IMMEDIATELY(1, "立即显示"),
/**向左移动*/
    NET_EM_LXSJ_SHOWMODE_TO_LEFT(2, "向左移动"),
/**向上移动*/
    NET_EM_LXSJ_SHOWMODE_TO_TOP(3, "向上移动"),
/**向右移动*/
    NET_EM_LXSJ_SHOWMODE_TO_RIGHT(4, "向右移动"),
/**向下移动*/
    NET_EM_LXSJ_SHOWMODE_TO_BOTTOM(5, "向下移动"),
/**闪烁*/
    NET_EM_LXSJ_SHOWMODE_BLINK(6, "闪烁"),
/**向上连移*/
    NET_EM_LXSJ_SHOWMODE_TO_TOP_CONTINUOUS(94, "向上连移"),
/**向下连移*/
    NET_EM_LXSJ_SHOWMODE_TO_BOTTOM_CONTINUOUS(95, "向下连移"),
/**向右连移*/
    NET_EM_LXSJ_SHOWMODE_TO_RIGHT_CONTINUOUS(96, "向右连移"),
/**向左连移*/
    NET_EM_LXSJ_SHOWMODE_TO_LEFT_CONTINUOUS(97, "向左连移");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

NET_EM_LXSJ_SHOWMODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (NET_EM_LXSJ_SHOWMODE enumType : NET_EM_LXSJ_SHOWMODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (NET_EM_LXSJ_SHOWMODE enumType : NET_EM_LXSJ_SHOWMODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static NET_EM_LXSJ_SHOWMODE getEnum(int value) {
        for (NET_EM_LXSJ_SHOWMODE e : NET_EM_LXSJ_SHOWMODE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return NET_EM_LXSJ_SHOWMODE.NET_EM_LXSJ_SHOWMODE_UNKNOWN;
    }
}


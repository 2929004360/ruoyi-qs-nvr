package com.ruoyi.dahua.lib.enumeration;


public enum NET_EM_LXSJ_COLORTYPE {
/**未知*/
    NET_EM_LXSJ_COLORTYPE_UNKNOWN(-1, "未知"),
/**单色*/
    NET_EM_LXSJ_COLORTYPE_SINGLE_COLOR(1, "单色"),
/**双色*/
    NET_EM_LXSJ_COLORTYPE_DOUBLE_COLOR(2, "双色"),
/**三色*/
    NET_EM_LXSJ_COLORTYPE_THREE_COLOR(3, "三色");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

NET_EM_LXSJ_COLORTYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (NET_EM_LXSJ_COLORTYPE enumType : NET_EM_LXSJ_COLORTYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (NET_EM_LXSJ_COLORTYPE enumType : NET_EM_LXSJ_COLORTYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static NET_EM_LXSJ_COLORTYPE getEnum(int value) {
        for (NET_EM_LXSJ_COLORTYPE e : NET_EM_LXSJ_COLORTYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return NET_EM_LXSJ_COLORTYPE.NET_EM_LXSJ_COLORTYPE_UNKNOWN;
    }
}


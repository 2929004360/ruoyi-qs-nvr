package com.ruoyi.dahua.lib.enumeration;


public enum NET_EM_LXSJ_DISPLAYTYPE {
/**未知*/
    NET_EM_LXSJ_DISPLAYTYPE_UNKNOWN(-1, "未知"),
/**0- 背景*/
    NET_EM_LXSJ_DISPLAYTYPE_BACK_TYPE(0, "0- 背景"),
/**1- 前景*/
    NET_EM_LXSJ_DISPLAYTYPE_FRONT_TYPE(1, "1- 前景");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

NET_EM_LXSJ_DISPLAYTYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (NET_EM_LXSJ_DISPLAYTYPE enumType : NET_EM_LXSJ_DISPLAYTYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (NET_EM_LXSJ_DISPLAYTYPE enumType : NET_EM_LXSJ_DISPLAYTYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static NET_EM_LXSJ_DISPLAYTYPE getEnum(int value) {
        for (NET_EM_LXSJ_DISPLAYTYPE e : NET_EM_LXSJ_DISPLAYTYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return NET_EM_LXSJ_DISPLAYTYPE.NET_EM_LXSJ_DISPLAYTYPE_UNKNOWN;
    }
}


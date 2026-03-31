package com.ruoyi.dahua.lib.enumeration;


public enum NET_EM_LXSJ_AREATYPE {
/**未知*/
    NET_EM_LXSJ_AREATYPE_UNKNOWN(-1, "未知"),
/**0- 静态*/
    NET_EM_LXSJ_AREATYPE_STATIC_TYPE(0, "0- 静态"),
/**1- 动态*/
    NET_EM_LXSJ_AREATYPE_DYNAMIC_TYPE(1, "1- 动态");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

NET_EM_LXSJ_AREATYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (NET_EM_LXSJ_AREATYPE enumType : NET_EM_LXSJ_AREATYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (NET_EM_LXSJ_AREATYPE enumType : NET_EM_LXSJ_AREATYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static NET_EM_LXSJ_AREATYPE getEnum(int value) {
        for (NET_EM_LXSJ_AREATYPE e :NET_EM_LXSJ_AREATYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return NET_EM_LXSJ_AREATYPE.NET_EM_LXSJ_AREATYPE_UNKNOWN;
    }
}


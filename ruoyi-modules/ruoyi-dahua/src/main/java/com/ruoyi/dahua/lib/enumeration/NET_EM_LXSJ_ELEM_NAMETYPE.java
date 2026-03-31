package com.ruoyi.dahua.lib.enumeration;


public enum NET_EM_LXSJ_ELEM_NAMETYPE {
/**未知*/
    NET_EM_LXSJ_ELEM_NAMETYPE_UNKNOWN(-1, "未知"),
/**0-分隔符*/
    NET_EM_LXSJ_ELEM_NAMETYPE_SEPERATER(0, "0-分隔符"),
/**1-换行符*/
    NET_EM_LXSJ_ELEM_NAMETYPE_NEWLINE(1, "1-换行符"),
/**2-车牌*/
    NET_EM_LXSJ_ELEM_NAMETYPE_PLATE(2, "2-车牌"),
/**3-违章信息*/
    NET_EM_LXSJ_ELEM_NAMETYPE_VIOLATIONINFO(3, "3-违章信息"),
/**4-速度*/
    NET_EM_LXSJ_ELEM_NAMETYPE_SPEED(4, "4-速度"),
/**5-自定义项*/
    NET_EM_LXSJ_ELEM_NAMETYPE_CUSTOM(5, "5-自定义项");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

NET_EM_LXSJ_ELEM_NAMETYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (NET_EM_LXSJ_ELEM_NAMETYPE enumType : NET_EM_LXSJ_ELEM_NAMETYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (NET_EM_LXSJ_ELEM_NAMETYPE enumType : NET_EM_LXSJ_ELEM_NAMETYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static NET_EM_LXSJ_ELEM_NAMETYPE getEnum(int value) {
        for (NET_EM_LXSJ_ELEM_NAMETYPE e : NET_EM_LXSJ_ELEM_NAMETYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return NET_EM_LXSJ_ELEM_NAMETYPE.NET_EM_LXSJ_ELEM_NAMETYPE_UNKNOWN;
    }
}


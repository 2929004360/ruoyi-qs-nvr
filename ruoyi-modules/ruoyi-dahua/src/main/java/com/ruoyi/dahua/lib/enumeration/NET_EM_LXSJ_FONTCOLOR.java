package com.ruoyi.dahua.lib.enumeration;


public enum NET_EM_LXSJ_FONTCOLOR {
/**未知*/
    NET_EM_LXSJ_FONTCOLOR_UNKNOWN(-1, "未知"),
/**0-红灯、绿灯、蓝灯都不亮*/
    NET_EM_LXSJ_FONTCOLOR_R0_G0_B0(0, "0-红灯、绿灯、蓝灯都不亮"),
/**1-红灯亮、绿灯不亮、蓝灯不亮*/
    NET_EM_LXSJ_FONTCOLOR_R1_G0_B0(1, "1-红灯亮、绿灯不亮、蓝灯不亮"),
/**2-红灯不亮、绿灯亮、蓝灯不亮*/
    NET_EM_LXSJ_FONTCOLOR_R0_G1_B0(2, "2-红灯不亮、绿灯亮、蓝灯不亮"),
/**3-红灯亮、绿灯亮、蓝灯不亮*/
    NET_EM_LXSJ_FONTCOLOR_R1_G1_B0(3, "3-红灯亮、绿灯亮、蓝灯不亮"),
/**4-红灯不亮、绿灯不亮、蓝灯亮*/
    NET_EM_LXSJ_FONTCOLOR_R0_G0_B1(4, "4-红灯不亮、绿灯不亮、蓝灯亮"),
/**5-红灯亮、绿灯不亮、蓝灯亮*/
    NET_EM_LXSJ_FONTCOLOR_R1_G0_B1(5, "5-红灯亮、绿灯不亮、蓝灯亮"),
/**6-红灯不亮、绿灯亮、蓝灯不亮*/
    NET_EM_LXSJ_FONTCOLOR_R0_G1_B1(6, "6-红灯不亮、绿灯亮、蓝灯不亮"),
/**7-红灯、绿灯、蓝灯全亮*/
    NET_EM_LXSJ_FONTCOLOR_R1_G1_B(7, "7-红灯、绿灯、蓝灯全亮");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

NET_EM_LXSJ_FONTCOLOR(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (NET_EM_LXSJ_FONTCOLOR enumType : NET_EM_LXSJ_FONTCOLOR.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (NET_EM_LXSJ_FONTCOLOR enumType : NET_EM_LXSJ_FONTCOLOR.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static NET_EM_LXSJ_FONTCOLOR getEnum(int value) {
        for (NET_EM_LXSJ_FONTCOLOR e : NET_EM_LXSJ_FONTCOLOR.values()) {
            if (e.getValue() == value)
                return e;
        }
        return NET_EM_LXSJ_FONTCOLOR.NET_EM_LXSJ_FONTCOLOR_UNKNOWN;
    }
}


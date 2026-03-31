package com.ruoyi.dahua.lib.enumeration;

/**
 * 文字大小(字高宽点数)
 */
public enum NET_EM_LXSJ_FONTSIZE {
/**未知*/
    NET_EM_LXSJ_FONTSIZE_UNKNOWN(-1, "未知"),
/**16点阵*/
    NET_EM_LXSJ_FONTSIZE_16(16, "16点阵"),
/**32点阵*/
    NET_EM_LXSJ_FONTSIZE_32(32, "32点阵");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

NET_EM_LXSJ_FONTSIZE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (NET_EM_LXSJ_FONTSIZE enumType : NET_EM_LXSJ_FONTSIZE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (NET_EM_LXSJ_FONTSIZE enumType : NET_EM_LXSJ_FONTSIZE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static NET_EM_LXSJ_FONTSIZE getEnum(int value) {
        for (NET_EM_LXSJ_FONTSIZE e : NET_EM_LXSJ_FONTSIZE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return NET_EM_LXSJ_FONTSIZE.NET_EM_LXSJ_FONTSIZE_UNKNOWN;
    }
}


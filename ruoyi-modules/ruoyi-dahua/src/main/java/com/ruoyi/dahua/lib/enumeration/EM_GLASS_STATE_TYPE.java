package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  戴眼镜状态 
* @date 2022/09/28 10:27:56
*/
public enum EM_GLASS_STATE_TYPE {
/**
未知
*/
    EM_GLASS_STATE_TYPE_UNKNOWN(0, "未知"),
/**
未戴
*/
    EM_GLASS_STATE_TYPE_NOT_WEARING(1, "未戴"),
/**
戴常规眼镜
*/
    EM_GLASS_STATE_TYPE_CONVENTIONAL_GLASSES(2, "戴常规眼镜"),
/**
戴太阳眼镜
*/
    EM_GLASS_STATE_TYPE_SUN_GLASSES(3, "戴太阳眼镜"),
/**
戴黑框眼镜
*/
    EM_GLASS_STATE_TYPE_BLACK_GLASSES(4, "戴黑框眼镜");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_GLASS_STATE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_GLASS_STATE_TYPE enumType : EM_GLASS_STATE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_GLASS_STATE_TYPE enumType : EM_GLASS_STATE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_GLASS_STATE_TYPE getEnum(int value) {
        for (EM_GLASS_STATE_TYPE e : EM_GLASS_STATE_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_GLASS_STATE_TYPE.EM_GLASS_STATE_TYPE_UNKNOWN;
    }
}


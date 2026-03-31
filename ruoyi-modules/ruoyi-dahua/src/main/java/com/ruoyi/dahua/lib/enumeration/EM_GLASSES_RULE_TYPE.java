package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 251823
* @description  眼镜检测规则中报警类型 
* @date 2022/11/01 19:47:17
*/
public enum EM_GLASSES_RULE_TYPE {
/**
未知
*/
    EM_GLASSES_RULE_TYPE_UNKNOWN(-1, "未知"),
/**
无眼镜
*/
    EM_GLASSES_RULE_TYPE_NO_GLASSES(0, "无眼镜"),
/**
太阳镜
*/
    EM_GLASSES_RULE_TYPE_SUN_GLASSES(1, "太阳镜"),
/**
黑框眼镜
*/
    EM_GLASSES_RULE_TYPE_BLACK_RIMMED_GLASSES(2, "黑框眼镜"),
/**
半框眼镜
*/
    EM_GLASSES_RULE_TYPE_HALF_RIMMED_GLASSES(3, "半框眼镜"),
/**
无框眼镜
*/
    EM_GLASSES_RULE_TYPE_RIMLESS_GLASSES(4, "无框眼镜"),
/**
普通眼镜
*/
    EM_GLASSES_RULE_TYPE_NORMAL_GLASSES(5, "普通眼镜"),
/**
工业护目镜
*/
    EM_GLASSES_RULE_TYPE_INDUSTRIAL_SAFETY_GLASSES(6, "工业护目镜");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_GLASSES_RULE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_GLASSES_RULE_TYPE enumType : EM_GLASSES_RULE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_GLASSES_RULE_TYPE enumType : EM_GLASSES_RULE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_GLASSES_RULE_TYPE getEnum(int value) {
        for (EM_GLASSES_RULE_TYPE e : EM_GLASSES_RULE_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_GLASSES_RULE_TYPE.EM_GLASSES_RULE_TYPE_UNKNOWN;
    }
}


package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 291189
 * @version 1.0
 * @description  处理方式
 * @date 2021/9/9
 */
public enum EM_PROCESS_MODE {
/**未知*/
    EM_PROCESS_MODE_UNKNOWN(-1, "未知"),
/**自弃*/
    EM_PROCESS_MODE_ABANDON_ONESELF(0, "自弃"),
/**放行*/
    EM_PROCESS_MODE_RELEASE(1, "放行"),
/**物品保管*/
    EM_PROCESS_MODE_STORAGE_OF_ARTICLES(2, "物品保管"),
/**移交公安*/
    EM_PROCESS_MODE_TRANSFER_TO_SECURITY(3, "移交公安"),
/**自行处理*/
    EM_PROCESS_MODE_DISCRETION(4, "自行处理"),
/**误报*/
    EM_PROCESS_MODE_FALSE_ALARM(5, "误报");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_PROCESS_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_PROCESS_MODE enumType : EM_PROCESS_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_PROCESS_MODE enumType : EM_PROCESS_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_PROCESS_MODE getEnum(int value) {
        for (EM_PROCESS_MODE e : EM_PROCESS_MODE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_PROCESS_MODE.EM_PROCESS_MODE_UNKNOWN;
    }
}


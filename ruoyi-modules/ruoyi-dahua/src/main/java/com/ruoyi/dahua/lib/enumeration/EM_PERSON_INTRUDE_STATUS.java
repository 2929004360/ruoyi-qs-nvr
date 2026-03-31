package com.ruoyi.dahua.lib.enumeration;

/**
 * 人员闯入报警标志
*/
public enum EM_PERSON_INTRUDE_STATUS
{
    /**
     * 未知
    */
    EM_PERSON_INTRUDE_STATUS_UNKNOWN(-1, "未知"),
    /**
     * 无人员闯入
    */
    EM_PERSON_INTRUDE_STATUS_UNMANNED(0, "无人员闯入"),
    /**
     * 有人员闯入
    */
    EM_PERSON_INTRUDE_STATUS_SOMEONE(1, "有人员闯入");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_PERSON_INTRUDE_STATUS(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_PERSON_INTRUDE_STATUS enumType : EM_PERSON_INTRUDE_STATUS.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_PERSON_INTRUDE_STATUS enumType : EM_PERSON_INTRUDE_STATUS.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}


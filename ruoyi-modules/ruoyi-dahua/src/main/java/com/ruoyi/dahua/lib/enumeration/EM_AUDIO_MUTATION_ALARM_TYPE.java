package com.ruoyi.dahua.lib.enumeration;

/**
 * 声音类型枚举
*/
public enum EM_AUDIO_MUTATION_ALARM_TYPE
{
    AUDIO_MUTATION_UNKNOW(0, ""),
    AUDIO_MUTATION_CRY(1, ""),
    AUDIO_MUTATION_ALARM(2, ""),
    AUDIO_MUTATION_GUNSHOT(3, ""),
    AUDIO_MUTATION_EXPLOSION(4, ""),
    AUDIO_MUTATION_SCREAM(5, ""),
    AUDIO_MUTATION_CRASHINGGLASS(6, "");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_AUDIO_MUTATION_ALARM_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_AUDIO_MUTATION_ALARM_TYPE enumType : EM_AUDIO_MUTATION_ALARM_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_AUDIO_MUTATION_ALARM_TYPE enumType : EM_AUDIO_MUTATION_ALARM_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}


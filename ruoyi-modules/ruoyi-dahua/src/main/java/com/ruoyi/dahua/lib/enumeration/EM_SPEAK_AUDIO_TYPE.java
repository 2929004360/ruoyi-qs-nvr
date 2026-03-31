package com.ruoyi.dahua.lib.enumeration;

/**
 * 音频编码压缩格式
*/
public enum EM_SPEAK_AUDIO_TYPE
{
    /**
     * 未知
    */
    EM_SPEAK_AUDIO_TYPE_UNKNOWN(0, "未知"),
    /**
     * PCM
    */
    EM_SPEAK_AUDIO_TYPE_PCM(1, "PCM"),
    /**
     * ADPCM
    */
    EM_SPEAK_AUDIO_TYPE_ADPCM(2, "ADPCM"),
    /**
     * G711A
    */
    EM_SPEAK_AUDIO_TYPE_G711A(3, "G711A"),
    /**
     * G711Mu
    */
    EM_SPEAK_AUDIO_TYPE_G711Mu(4, "G711Mu"),
    /**
     * G726
    */
    EM_SPEAK_AUDIO_TYPE_G726(5, "G726"),
    /**
     * G729
    */
    EM_SPEAK_AUDIO_TYPE_G729(6, "G729"),
    /**
     * MPEG2
    */
    EM_SPEAK_AUDIO_TYPE_MPEG2(7, "MPEG2"),
    /**
     * AMR
    */
    EM_SPEAK_AUDIO_TYPE_AMR(8, "AMR"),
    /**
     * AAC
    */
    EM_SPEAK_AUDIO_TYPE_AAC(9, "AAC");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_SPEAK_AUDIO_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_SPEAK_AUDIO_TYPE enumType : EM_SPEAK_AUDIO_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_SPEAK_AUDIO_TYPE enumType : EM_SPEAK_AUDIO_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}


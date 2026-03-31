package com.ruoyi.dahua.lib.enumeration;

/**
 * 语音编码类型
 *
 * @author 47040
 * @since Created at 2021/5/26 10:25
 */
public enum TALK_CODING_TYPE {
    NET_TALK_DEFAULT(0, "无头PCM"),
    NET_TALK_PCM(1, "带头PCM"),
    NET_TALK_G711a(2, "G711a"),
    NET_TALK_AMR(3, "AMR"),
    NET_TALK_G711u(4, "G711u"),
    NET_TALK_G726(5, "G726"),
    NET_TALK_G723_53(6, "G723_53"),
    NET_TALK_G723_63(7, "G723_63"),
    NET_TALK_AAC(8, "AAC"),
    NET_TALK_OGG(9, "OGG"),
    NET_TALK_G729(10, "G729"),
    NET_TALK_MPEG2(11, "MPEG2"),
    NET_TALK_MPEG2_Layer2(12, "MPEG2-Layer2"),
    NET_TALK_G722_1(13, "G.722.1"),
    NET_TALK_ADPCM(21, "ADPCM"),
    NET_TALK_MP3(22, "MP3");

    private final int value;
    private final String note;

    TALK_CODING_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    public static String getNoteByValue(int givenValue) {
        for (TALK_CODING_TYPE enumType : TALK_CODING_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (TALK_CODING_TYPE enumType : TALK_CODING_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static TALK_CODING_TYPE getEnum(int value) {
        for (TALK_CODING_TYPE e : TALK_CODING_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return TALK_CODING_TYPE.NET_TALK_DEFAULT;
    }
}


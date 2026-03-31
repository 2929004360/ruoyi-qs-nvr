package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 47040
 * @since Created at 2021/5/25 22:29
 */
public enum CFG_EM_PREVIEW_MODE {
    /**
     * 未知
     */
    CFG_EM_PREVIEW_MODE_UNKNOWN(0, "未知"),
    /**
     * 快照方式
     */
    CFG_EM_PREVIEW_MODE_SNAPSHOT(1, "快照方式"),
    /**
     * 分割编码方式
     */
    CFG_EM_PREVIEW_MODE_SPLITENCODE(2, "分割编码方式"),
    /**
     * 分割快照方式
     */
    CFG_EM_PREVIEW_MODE_SPLITSNAP(3, "分割快照方式");

    private final int value;
    private final String note;

    CFG_EM_PREVIEW_MODE(int givenValue, String note) {
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
        for (CFG_EM_PREVIEW_MODE enumType : CFG_EM_PREVIEW_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (CFG_EM_PREVIEW_MODE enumType : CFG_EM_PREVIEW_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static CFG_EM_PREVIEW_MODE getEnum(int value) {
        for (CFG_EM_PREVIEW_MODE e : CFG_EM_PREVIEW_MODE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return CFG_EM_PREVIEW_MODE.CFG_EM_PREVIEW_MODE_UNKNOWN;
    }
}


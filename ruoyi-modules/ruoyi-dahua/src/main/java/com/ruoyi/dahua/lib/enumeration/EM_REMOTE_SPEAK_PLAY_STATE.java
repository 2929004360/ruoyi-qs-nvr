package com.ruoyi.dahua.lib.enumeration;

/**
 * 开始放音状态
*/
public enum EM_REMOTE_SPEAK_PLAY_STATE
{
    /**
     * 未知
    */
    EM_REMOTE_SPEAK_PLAY_STATE_UNKNOWN(0, "未知"),
    /**
     * 表示操作成功
    */
    EM_REMOTE_SPEAK_PLAY_STATE_OK(1, "表示操作成功"),
    /**
     * 表示操作正在进行
    */
    EM_REMOTE_SPEAK_PLAY_STATE_BUSY(2, "表示操作正在进行"),
    /**
     * 表示其它错误
    */
    EM_REMOTE_SPEAK_PLAY_STATE_ERROR(3, "表示其它错误"),
    /**
     * 表示Ftp地址不在NAS中
    */
    EM_REMOTE_SPEAK_PLAY_STATE_FTP_NOT_VALID(4, "表示Ftp地址不在NAS中");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_REMOTE_SPEAK_PLAY_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_REMOTE_SPEAK_PLAY_STATE enumType : EM_REMOTE_SPEAK_PLAY_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_REMOTE_SPEAK_PLAY_STATE enumType : EM_REMOTE_SPEAK_PLAY_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}


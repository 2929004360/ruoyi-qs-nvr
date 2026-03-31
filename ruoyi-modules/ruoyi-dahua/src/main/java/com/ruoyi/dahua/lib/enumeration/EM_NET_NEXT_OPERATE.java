package com.ruoyi.dahua.lib.enumeration;

/**
 * 下一步的操作
*/
public enum EM_NET_NEXT_OPERATE
{
    /**
     * 未知操作
    */
    EM_NET_NEXT_OPERATE_UNKNOWN(0, "未知操作"),
    /**
     * 安装App;
    */
    EM_NET_NEXT_OPERATE_INSTALL(1, "安装App;"),
    /**
     * 更新App;
    */
    EM_NET_NEXT_OPERATE_UPDATE(2, "更新App;");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_NET_NEXT_OPERATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_NET_NEXT_OPERATE enumType : EM_NET_NEXT_OPERATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_NET_NEXT_OPERATE enumType : EM_NET_NEXT_OPERATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}


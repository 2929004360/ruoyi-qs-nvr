package com.ruoyi.dahua.lib.enumeration;

/**
 * 支持的操作
*/
public enum EM_NET_UPGRADE_ACTION
{
    /**
     * 未知操作;
    */
    EM_NET_UPGRADE_ACTION_UNKNOWN(0, "未知操作;"),
    /**
     * 安装APP;
    */
    EM_NET_UPGRADE_ACTION_INSTALLAPP(1, "安装APP;"),
    /**
     * 更新APP;
    */
    EM_NET_UPGRADE_ACTION_UPDATEAPP(2, "更新APP;"),
    /**
     * 安装主程序;
    */
    EM_NET_UPGRADE_ACTION_UPDATEMAINFIRMWARE(3, "安装主程序;"),
    /**
     * 更新证书;
    */
    EM_NET_UPGRADE_ACTION_UPDATELICENSE(4, "更新证书;");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_NET_UPGRADE_ACTION(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_NET_UPGRADE_ACTION enumType : EM_NET_UPGRADE_ACTION.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_NET_UPGRADE_ACTION enumType : EM_NET_UPGRADE_ACTION.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}


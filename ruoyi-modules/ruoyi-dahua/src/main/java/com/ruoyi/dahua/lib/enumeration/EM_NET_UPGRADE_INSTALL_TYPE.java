package com.ruoyi.dahua.lib.enumeration;

/**
 * CLIENT_UpgraderInstall接口的 操作类型
*/
public enum EM_NET_UPGRADE_INSTALL_TYPE
{
    /**
     * 升级准备				(对应结构体 NET_IN_INSTALL_PREPAREEX,	NET_OUT_INSTALL_PREPAREEX)
    */
    EM_UPGRADER_INSTALL_PREPAREEX(0, "升级准备				(对应结构体 NET_IN_INSTALL_PREPAREEX,	NET_OUT_INSTALL_PREPAREEX)"),
    /**
     * 升级数据				(对应结构体	NET_IN_INSTALL_APPEND_DATA,	NET_OUT_INSTALL_APPEND_DATA)
    */
    EM_UPGRADER_INSTALL_APPEND_DATA(1, "升级数据				(对应结构体	NET_IN_INSTALL_APPEND_DATA,	NET_OUT_INSTALL_APPEND_DATA)"),
    /**
     * 执行升级				(对应结构体 NET_IN_INSTALL_EXECUTE,		NET_OUT_INSTALL_EXECUTE)
    */
    EM_UPGRADER_INSTALL_EXECUTE(2, "执行升级				(对应结构体 NET_IN_INSTALL_EXECUTE,		NET_OUT_INSTALL_EXECUTE)"),
    /**
     * 获取升级状态			(对应结构体	NET_IN_INSTALL_GETSTATE，	NET_OUT_INSTALL_GETSTATE)
    */
    EM_UPGRADER_INSTALL_GETSTATE(3, "获取升级状态			(对应结构体	NET_IN_INSTALL_GETSTATE，	NET_OUT_INSTALL_GETSTATE)"),
    /**
     * 取消升级				(对应结构体 NET_IN_INSTALL_CANCEL,		NET_OUT_INSTALL_CANCEL)
    */
    EM_UPGRADER_INSTALL_CANCEL(4, "取消升级				(对应结构体 NET_IN_INSTALL_CANCEL,		NET_OUT_INSTALL_CANCEL)"),
    /**
     * 指定升级包地址进行升级	(对应结构体 NET_IN_INSTALL_FIRMWAREEX,	NET_OUT_INSTALL_FIRMWAREEX)
    */
    EM_UPGRADER_INSTALL_FIRMWAREEX(5, "指定升级包地址进行升级	(对应结构体 NET_IN_INSTALL_FIRMWAREEX,	NET_OUT_INSTALL_FIRMWAREEX)");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_NET_UPGRADE_INSTALL_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_NET_UPGRADE_INSTALL_TYPE enumType : EM_NET_UPGRADE_INSTALL_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_NET_UPGRADE_INSTALL_TYPE enumType : EM_NET_UPGRADE_INSTALL_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}


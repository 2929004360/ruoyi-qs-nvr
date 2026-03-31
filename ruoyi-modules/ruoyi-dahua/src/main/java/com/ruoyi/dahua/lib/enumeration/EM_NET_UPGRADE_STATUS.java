package com.ruoyi.dahua.lib.enumeration;

/**
 * 升级状态
*/
public enum EM_NET_UPGRADE_STATUS
{
    /**
     * 未知状态
    */
    EM_NET_UPGRADE_UNKNOWN(0, "未知状态"),
    /**
     * 准备升级;
    */
    EM_NET_UPGRADE_PREPARING(1, "准备升级;"),
    /**
     * 正在下载数据;
    */
    EM_NET_UPGRADE_DOWNLOADING(2, "正在下载数据;"),
    /**
     * 下载失败;
    */
    EM_NET_UPGRADE_DOWNLOADFAILED(3, "下载失败;"),
    /**
     * 正在升级;
    */
    EM_NET_UPGRADE_UPGRADING(4, "正在升级;"),
    /**
     * 升级包不正确;
    */
    EM_NET_UPGRADE_INVALID(5, "升级包不正确;"),
    /**
     * 升级失败;
    */
    EM_NET_UPGRADE_FAILED(6, "升级失败;"),
    /**
     * 升级成功;
    */
    EM_NET_UPGRADE_SUCCEEDED(7, "升级成功;"),
    /**
     * 取消升级;
    */
    EM_NET_UPGRADE_CANCELLED(8, "取消升级;"),
    /**
     * 内存不够;
    */
    EM_NET_UPGRADE_NOTENOUGHMEMORY(9, "内存不够;"),
    /**
     * 应用程序包不匹配;
    */
    EM_NET_UPGRADE_FILEUNMATCH(10, "应用程序包不匹配;"),
    /**
     * 版本不匹配;
    */
    EM_NET_UPGRADE_UNMATCHVERSION(11, "版本不匹配;"),
    /**
     * 应用程序版本相同;
    */
    EM_NET_UPGRADE_SAMEASOLDAPP(12, "应用程序版本相同;"),
    /**
     * 应用程序已存在;
    */
    EM_NET_UPGRADE_APPEXIST(13, "应用程序已存在;");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_NET_UPGRADE_STATUS(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_NET_UPGRADE_STATUS enumType : EM_NET_UPGRADE_STATUS.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_NET_UPGRADE_STATUS enumType : EM_NET_UPGRADE_STATUS.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}


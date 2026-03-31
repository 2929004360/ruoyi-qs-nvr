package com.ruoyi.dahua.lib.enumeration;

/**
 * 人员组类型
*/
public enum EM_PEOPLE_GROUP_TYPE
{
    /**
     * 未知
    */
    EM_PEOPLE_GROUP_TYPE_UNKNOWN(0, "未知"),
    /**
     * 历史数据库,保存视频通道的抓拍目标图片
    */
    EM_PEOPLE_GROUP_TYPE_HISTORYDB(1, "历史数据库,保存视频通道的抓拍目标图片"),
    /**
     * 禁止名单组,保存外部导入的图片
    */
    EM_PEOPLE_GROUP_TYPE_BLACKLISTDB(2, "禁止名单组,保存外部导入的图片"),
    /**
     * 报警组
    */
    EM_PEOPLE_GROUP_TYPE_ALARMDB(3, "报警组");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_PEOPLE_GROUP_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_PEOPLE_GROUP_TYPE enumType : EM_PEOPLE_GROUP_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_PEOPLE_GROUP_TYPE enumType : EM_PEOPLE_GROUP_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}


package com.ruoyi.dahua.lib.enumeration;
/**
 * @author 291189
 * @version 1.0
 * @description 操作类型枚举
 * @date 2022/7/19
 */
public enum NET_EVENT_OPERATE_TYPE {
    NET_EVENT_OPERATE_TYPE_UNKOWN(0, "未知"),
    NET_EVENT_OPERATE_TYPE_ADD(1, "增加"),
    NET_EVENT_OPERATE_TYPE_DELETE(2, "删除"),
    NET_EVENT_OPERATE_TYPE_MODIFY(3, "修改");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    NET_EVENT_OPERATE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (NET_EVENT_OPERATE_TYPE enumType : NET_EVENT_OPERATE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NET_EVENT_OPERATE_TYPE enumType : NET_EVENT_OPERATE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static NET_EVENT_OPERATE_TYPE getEnum(int value) {
        for (NET_EVENT_OPERATE_TYPE e : NET_EVENT_OPERATE_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return NET_EVENT_OPERATE_TYPE.NET_EVENT_OPERATE_TYPE_UNKOWN;
    }
}


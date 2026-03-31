package com.ruoyi.dahua.lib.enumeration;

/**
 * 手机登陆时过滤可选方式,按照类型进行|或操作。
*/
public enum EM_OPTTYPE_MOBILE_TYPE
{
    /**
     * 设备属性
    */
    OPTTYPE_MOBILE_DEVICE_ATTR(1, "设备属性"),
    /**
     * 设备序列号
    */
    OPTTYPE_MOBILE_DEVICE_SN(2, "设备序列号"),
    /**
     * 硬盘信息
    */
    OPTTYPE_MOBILE_DISK_INFO(4, "硬盘信息"),
    /**
     * 数字通道个数
    */
    OPTTYPE_MOBILE_DIGITAL_NUM(8, "数字通道个数"),
    /**
     * 报警输入输出
    */
    OPTTYPE_MOBILE_ALARM_IO(16, "报警输入输出"),
    /**
     * 能力集
    */
    OPTTYPE_MOBILE_DEVICE_ABILITY(32, "能力集"),
    /**
     * 查询软件版本信息APP优化
    */
    OPTTYPE_MOBILE_STATE_SOFTWARE(4096, "查询软件版本信息APP优化");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_OPTTYPE_MOBILE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_OPTTYPE_MOBILE_TYPE enumType : EM_OPTTYPE_MOBILE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_OPTTYPE_MOBILE_TYPE enumType : EM_OPTTYPE_MOBILE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}


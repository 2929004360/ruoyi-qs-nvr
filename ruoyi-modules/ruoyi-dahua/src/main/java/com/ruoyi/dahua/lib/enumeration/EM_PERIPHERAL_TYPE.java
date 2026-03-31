package com.ruoyi.dahua.lib.enumeration;

/**
 * 外设类型
*/
public enum EM_PERIPHERAL_TYPE
{
    /**
     * 未知
    */
    EM_PERIPHERAL_NUKNOWN(0, "未知"),
    /**
     * 闸机控制板
    */
    EM_PERIPHERAL_ASG_CONTROLLER(1, "闸机控制板"),
    /**
     * 闸机电机驱动
    */
    EM_PERIPHERAL_ASG_MOTOR(2, "闸机电机驱动"),
    /**
     * 安检门门板控制器
    */
    EM_PERIPHERAL_SECURITYGATE_DOOR_CONTROLLER(3, "安检门门板控制器"),
    /**
     * 红外测温模块
    */
    EM_PERIPHERAL_INFRARED_TEMPERATURE_UNIT(4, "红外测温模块"),
    /**
     * 闸机语音模块
    */
    EM_PERIPHERAL_ASG_VOICE(5, "闸机语音模块"),
    /**
     * 闸机读卡器
    */
    EM_PERIPHERAL_ASG_READER(6, "闸机读卡器"),
    /**
     * 信号机通讯板
    */
    EM_PERIPHERAL_RTSCCOMMBOARD(7, "信号机通讯板"),
    /**
     * 单片机
    */
    EM_PERIPHERAL_SINGLECHIPMICROCOMPUTER(8, "单片机"),
    /**
     * 重合闸
    */
    EM_PERIPHERAL_RECLOSING(9, "重合闸"),
    /**
     * 空开
    */
    EM_PERIPHERAL_AIRSWITCHVERSION(10, "空开"),
    /**
     * 国密芯片COS版本
    */
    EM_PERIPHERAL_COS(11, "国密芯片COS版本");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_PERIPHERAL_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_PERIPHERAL_TYPE enumType : EM_PERIPHERAL_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_PERIPHERAL_TYPE enumType : EM_PERIPHERAL_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}


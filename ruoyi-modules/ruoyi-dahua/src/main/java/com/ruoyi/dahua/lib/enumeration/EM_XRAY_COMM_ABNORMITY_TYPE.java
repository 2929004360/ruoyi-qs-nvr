package com.ruoyi.dahua.lib.enumeration;

/**
 * X光机故障类型
*/
public enum EM_XRAY_COMM_ABNORMITY_TYPE
{
    /**
     * 未知
    */
    EM_XRAY_COMM_ABNORMITY_TYPE_UNKNWON(-1, "未知"),
    /**
     * 射线源监管故障
    */
    EM_XRAY_COMM_ABNORMITY_TYPE_SOURCE_SUPERVISION_FAILURE(0, "射线源监管故障"),
    /**
     * 射线源互锁故障
    */
    EM_XRAY_COMM_ABNORMITY_TYPE_SOURCE_INTERLOCK_FAILURE(1, "射线源互锁故障"),
    /**
     * 射线源负压过高
    */
    EM_XRAY_COMM_ABNORMITY_TYPE_SOURCE_HIGH_NEGATIVE_PRESSURE(2, "射线源负压过高"),
    /**
     * 射线源正压过高
    */
    EM_XRAY_COMM_ABNORMITY_TYPE_SOURCE_HIGH_POSITIVE_PRESSURE(3, "射线源正压过高"),
    /**
     * 射线源温度过高
    */
    EM_XRAY_COMM_ABNORMITY_TYPE_SOURCE_HIGH_TEMPERATURE(4, "射线源温度过高"),
    /**
     * 射线源电弧故障
    */
    EM_XRAY_COMM_ABNORMITY_TYPE_SOURCE_ARC_FAULT(5, "射线源电弧故障"),
    /**
     * 射线源电流过大
    */
    EM_XRAY_COMM_ABNORMITY_TYPE_SOURCE_OVERCURRENT(6, "射线源电流过大"),
    /**
     * 射线源功率限制
    */
    EM_XRAY_COMM_ABNORMITY_TYPE_SOURCE_POWER_LIMIT(7, "射线源功率限制"),
    /**
     * 射线源电压过高
    */
    EM_XRAY_COMM_ABNORMITY_TYPE_SOURCE_HIGH_VOLTAGE(8, "射线源电压过高"),
    /**
     * 单片机与射线源通信异常
    */
    EM_XRAY_COMM_ABNORMITY_TYPE_COMMUNICATION_ERROR1(9, "单片机与射线源通信异常"),
    /**
     * 服务器与工控板通信异常
    */
    EM_XRAY_COMM_ABNORMITY_TYPE_COMMUNICATION_ERROR2(10, "服务器与工控板通信异常"),
    /**
     * 服务器与探测器通信异常
    */
    EM_XRAY_COMM_ABNORMITY_TYPE_COMMUNICATION_ERROR3(11, "服务器与探测器通信异常"),
    /**
     * A端节能传感器被遮挡或未对齐
    */
    EM_XRAY_COMM_ABNORMITY_TYPE_SENSOR1_A_BLOCKED(12, "A端节能传感器被遮挡或未对齐"),
    /**
     * B端节能传感器被遮挡或未对齐
    */
    EM_XRAY_COMM_ABNORMITY_TYPE_SENSOR1_B_BLOCKED(13, "B端节能传感器被遮挡或未对齐"),
    /**
     * A端过包传感器被遮挡或未对齐
    */
    EM_XRAY_COMM_ABNORMITY_TYPE_SENSOR2_A_BLOCKED(14, "A端过包传感器被遮挡或未对齐"),
    /**
     * B端过包传感器被遮挡或未对齐
    */
    EM_XRAY_COMM_ABNORMITY_TYPE_SENSOR2_B_BLOCKED(15, "B端过包传感器被遮挡或未对齐"),
    /**
     * 射线源欠压
    */
    EM_XRAY_COMM_ABNORMITY_TYPE_SOURCE_UNDERVOLTAGE(16, "射线源欠压"),
    /**
     * 射线源欠流
    */
    EM_XRAY_COMM_ABNORMITY_TYPE_SOURCE_UNDERCURRENT(17, "射线源欠流"),
    /**
     * 电机过热
    */
    EM_XRAY_COMM_ABNORMITY_TYPE_MOTOR_OVERHEATED(18, "电机过热");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_XRAY_COMM_ABNORMITY_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_XRAY_COMM_ABNORMITY_TYPE enumType : EM_XRAY_COMM_ABNORMITY_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_XRAY_COMM_ABNORMITY_TYPE enumType : EM_XRAY_COMM_ABNORMITY_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}


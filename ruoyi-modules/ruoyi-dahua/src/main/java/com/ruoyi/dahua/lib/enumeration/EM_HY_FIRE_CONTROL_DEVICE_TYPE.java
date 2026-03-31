package com.ruoyi.dahua.lib.enumeration;

/**
 * 消防离线故障事件设备类型
*/
public enum EM_HY_FIRE_CONTROL_DEVICE_TYPE
{
    /**
     * 未知
    */
    EM_HY_FIRE_CONTROL_DEVICE_TYPE_UNKNOWN(0, "未知"),
    /**
     * 无线报警主机
    */
    EM_HY_FIRE_CONTROL_DEVICE_TYPE_WIRELESSALARMHOST(1, "无线报警主机"),
    /**
     * 智能用电监控设备
    */
    EM_HY_FIRE_CONTROL_DEVICE_TYPE_INTELLIELECMONITORDEV(2, "智能用电监控设备"),
    /**
     * 图像型烟感
    */
    EM_HY_FIRE_CONTROL_DEVICE_TYPE_IMAGETYPESMOKEDETECTOR(3, "图像型烟感"),
    /**
     * 火焰识别摄像机
    */
    EM_HY_FIRE_CONTROL_DEVICE_TYPE_FLAMERECOGNITIONCAMERA(4, "火焰识别摄像机"),
    /**
     * 感烟火灾探测器
    */
    EM_HY_FIRE_CONTROL_DEVICE_TYPE_SMOKEFIREDETECTOR(5, "感烟火灾探测器"),
    /**
     * 感温火灾探测器
    */
    EM_HY_FIRE_CONTROL_DEVICE_TYPE_TEMPERATUREFIREDETECTOR(6, "感温火灾探测器"),
    /**
     * 可燃气体探测器
    */
    EM_HY_FIRE_CONTROL_DEVICE_TYPE_COMBUSTIBLEGASDETECTOR(7, "可燃气体探测器"),
    /**
     * 手动报警按钮
    */
    EM_HY_FIRE_CONTROL_DEVICE_TYPE_HANDLEALARMBUTTON(8, "手动报警按钮"),
    /**
     * 声光报警器
    */
    EM_HY_FIRE_CONTROL_DEVICE_TYPE_SOUNDLIGHTALARM(9, "声光报警器"),
    /**
     * 故障电弧传感器
    */
    EM_HY_FIRE_CONTROL_DEVICE_TYPE_FAULTELECTRICARCSENSOR(10, "故障电弧传感器");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_HY_FIRE_CONTROL_DEVICE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_HY_FIRE_CONTROL_DEVICE_TYPE enumType : EM_HY_FIRE_CONTROL_DEVICE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_HY_FIRE_CONTROL_DEVICE_TYPE enumType : EM_HY_FIRE_CONTROL_DEVICE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}


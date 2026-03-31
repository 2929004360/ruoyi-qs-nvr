package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 260611
 * @description 雷达操作类型
 * @date 2022/08/04 10:13:33
 */
public enum EM_RADAR_OPERATE_TYPE {
    /**
     * 雷达开始升级通知,入参:NET_IN_RADAR_STARTUPGRADE,出参:NET_OUT_RADAR_STARTUPGRADE
     */
    EM_RADAR_OPERATE_TYPE_STARTUPGRADE(0, "雷达开始升级通知,入参:NET_IN_RADAR_STARTUPGRADE,出参:NET_OUT_RADAR_STARTUPGRADE"),
    /**
     * 获取雷达能力,入参:NET_IN_RADAR_GETCAPS,出参:NET_OUT_RADAR_GETCAPS
     */
    EM_RADAR_OPERATE_TYPE_GETCAPS(1, "获取雷达能力,入参:NET_IN_RADAR_GETCAPS,出参:NET_OUT_RADAR_GETCAPS"),
    /**
     * 获取雷达状态,入参:NET_IN_RADAR_GETSTATUS,出参:NET_OUT_RADAR_GETSTATUS
     */
    EM_RADAR_OPERATE_TYPE_GETSTATUS(2, "获取雷达状态,入参:NET_IN_RADAR_GETSTATUS,出参:NET_OUT_RADAR_GETSTATUS"),
    /**
     * 计算图片对应的地图实际长度,入参:NET_IN_RADAR_CALCULATEREALSIZE,出参:NET_OUT_RADAR_CALCULATEREALSIZE
     */
    EM_RADAR_OPERATE_TYPE_CALCULATEREALSIZE(3, "计算图片对应的地图实际长度,入参:NET_IN_RADAR_CALCULATEREALSIZE,出参:NET_OUT_RADAR_CALCULATEREALSIZE"),
    /**
     * 雷球校准配置下发,入参:NET_IN_RADAR_SETCALIBRATION,出参:NET_OUT_RADAR_SETCALIBRATION
     */
    EM_RADAR_OPERATE_TYPE_SETCALIBRATION(4, "雷球校准配置下发,入参:NET_IN_RADAR_SETCALIBRATION,出参:NET_OUT_RADAR_SETCALIBRATION");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_RADAR_OPERATE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_RADAR_OPERATE_TYPE enumType : EM_RADAR_OPERATE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_RADAR_OPERATE_TYPE enumType : EM_RADAR_OPERATE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_RADAR_OPERATE_TYPE getEnum(int value) {
        for (EM_RADAR_OPERATE_TYPE e : EM_RADAR_OPERATE_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_RADAR_OPERATE_TYPE.EM_RADAR_OPERATE_TYPE_STARTUPGRADE;
    }
}


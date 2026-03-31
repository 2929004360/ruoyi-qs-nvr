package com.ruoyi.dahua.lib.enumeration;

/**
 * EM_ELEMENT_EVENT_TYPE 播放事件类型
*/
public enum EM_VOICE_BROADCAST_EVENT_TYPE
{
    /**
     * "",未知
    */
    EM_EVENT_TYPE_UNKOWN(0, "\"\",未知"),
    /**
     * "Parking",车位有车
    */
    EM_EVENT_TYPE_PARKING(1, "\"Parking\",车位有车"),
    /**
     * "NoParking",车位无车
    */
    EM_EVENT_TYPE_NO_PARKING(2, "\"NoParking\",车位无车"),
    /**
     * "OverLine",车位压线
    */
    EM_EVENT_TYPE_OVERLINE(3, "\"OverLine\",车位压线"),
    /**
     * "NotAllowOccupy", 非允许名单车占用
    */
    EM_EVENT_TYPE_NOT_ALLOW_OCCUPY(4, "\"NotAllowOccupy\", 非允许名单车占用"),
    /**
     * "NotNEOccupy", 非新能源车占用
    */
    EM_EVENT_TYPE_NOT_NE_OCCUPY(5, "\"NotNEOccupy\", 非新能源车占用"),
    /**
     * "SmallOccupyLarge", 小车占用大车位
    */
    EM_EVENT_TYPE_SMALL_OCCUPY_LARGE(6, "\"SmallOccupyLarge\", 小车占用大车位"),
    /**
     * "AllowListIn", 允许名单车驶入
    */
    EM_EVENT_TYPE_ALLOW_LIST_IN(7, "\"AllowListIn\", 允许名单车驶入"),
    /**
     * "AreaInvasion", 区域入侵
    */
    EM_EVENT_TYPE_AREA_INVASION(8, "\"AreaInvasion\", 区域入侵"),
    /**
     * "AreaStay", 区域滞留
    */
    EM_EVENT_TYPE_AREA_STAY(9, "\"AreaStay\", 区域滞留"),
    /**
     * "OpenStrobeAlarm", 开闸警戒
    */
    EM_EVENT_TYPE_OPEN_STROBE_ALARM(10, "\"OpenStrobeAlarm\", 开闸警戒"),
    /**
     * "NotAllowEntry", 异常驶入
    */
    EM_EVENT_TYPE_NOT_ALLOW_ENTRY(11, "\"NotAllowEntry\", 异常驶入"),
    /**
     * "NotAllowEnter", 非允许名单车辆驶入
    */
    EM_EVENT_TYPE_NOT_ALLOW_ENTER(12, "\"NotAllowEnter\", 非允许名单车辆驶入"),
    /**
     * "NotNEEnter", 非新能源车辆驶入
    */
    EM_EVENT_TYPE_NOT_NE_ENTER(13, "\"NotNEEnter\", 非新能源车辆驶入"),
    /**
     * "FireDetection", 火焰检测
    */
    EM_EVENT_TYPE_FIRE_DETECTION(14, "\"FireDetection\", 火焰检测"),
    /**
     * "SmokeDetection", 烟雾报警
    */
    EM_EVENT_TYPE_SMOKE_DETECTION(15, "\"SmokeDetection\", 烟雾报警"),
    /**
     * "IllegalParking", 车辆违停
    */
    EM_EVENT_TYPE_ILLEGAL_PARKING(16, "\"IllegalParking\", 车辆违停"),
    /**
     * "ForeignObjectOccupy", 异物占位
    */
    EM_EVENT_TYPE_FOREIGN_OBJECT_OCCUPY(17, "\"ForeignObjectOccupy\", 异物占位");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_VOICE_BROADCAST_EVENT_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_VOICE_BROADCAST_EVENT_TYPE enumType : EM_VOICE_BROADCAST_EVENT_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_VOICE_BROADCAST_EVENT_TYPE enumType : EM_VOICE_BROADCAST_EVENT_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}


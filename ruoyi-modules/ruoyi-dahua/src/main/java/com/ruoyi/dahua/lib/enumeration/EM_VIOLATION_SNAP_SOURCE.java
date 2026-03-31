package com.ruoyi.dahua.lib.enumeration;

/**
 * 抓拍触发源
*/
public enum EM_VIOLATION_SNAP_SOURCE
{
    /**
     * 未知，不存在此字段时默认为0
    */
    EM_VIOLATION_SNAP_SOURCE_TYPE_UNKNOWN(0, "未知，不存在此字段时默认为0"),
    /**
     * 线圈
    */
    EM_VIOLATION_SNAP_SOURCE_TYPE_COIL(1, "线圈"),
    /**
     * 雷达
    */
    EM_VIOLATION_SNAP_SOURCE_TYPE_RADAR(2, "雷达"),
    /**
     * 视频
    */
    EM_VIOLATION_SNAP_SOURCE_TYPE_VIDEO(3, "视频"),
    /**
     * 视频和线圈混合
    */
    EM_VIOLATION_SNAP_SOURCE_TYPE_VIDEO_COIL(4, "视频和线圈混合"),
    /**
     * 视频和雷达混合
    */
    EM_VIOLATION_SNAP_SOURCE_TYPE_VIDEO_RADAR(5, "视频和雷达混合"),
    /**
     * 视频、线圈和雷达混合
    */
    EM_VIOLATION_SNAP_SOURCE_TYPE_VIDEO_COIL_RADAR(6, "视频、线圈和雷达混合"),
    /**
     * 强制触发
    */
    EM_VIOLATION_SNAP_SOURCE_TYPE_FORCE_TRIGER(7, "强制触发"),
    /**
     * 车位锁状态
    */
    EM_VIOLATION_SNAP_SOURCE_TYPE_PARKING_LOCK_STATUS(8, "车位锁状态"),
    /**
     * 道闸状态
    */
    EM_VIOLATION_SNAP_SOURCE_TYPE_GATE_STATUS(9, "道闸状态"),
    /**
     * 外设状态
    */
    EM_VIOLATION_SNAP_SOURCE_TYPE_PERIPHERAL_STATUS(10, "外设状态"),
    /**
     * 驶入
    */
    EM_VIOLATION_SNAP_SOURCE_TYPE_DRIVE_IN(11, "驶入"),
    /**
     * 驶出
    */
    EM_VIOLATION_SNAP_SOURCE_TYPE_DRIVE_OUT(12, "驶出");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_VIOLATION_SNAP_SOURCE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_VIOLATION_SNAP_SOURCE enumType : EM_VIOLATION_SNAP_SOURCE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_VIOLATION_SNAP_SOURCE enumType : EM_VIOLATION_SNAP_SOURCE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}


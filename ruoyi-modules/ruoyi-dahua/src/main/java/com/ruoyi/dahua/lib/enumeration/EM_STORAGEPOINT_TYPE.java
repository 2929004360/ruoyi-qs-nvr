package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 421657
 * @description 存储点类型
 * @origin autoTool
 * @date 2023/10/30 15:54:18
 */
public enum EM_STORAGEPOINT_TYPE {
    /**
     *
     */
    EM_STORAGE_TIMINGRECORD(0, "定时录像存储点类"),
    /**
     *
     */
    EM_STORAGE_MANUALRECORD(1, "手动录像存储点类"),
    /**
     *
     */
    EM_STORAGE_VIDEODETECTRECORD(2, "视频检测录像存储点类"),
    /**
     *
     */
    EM_STORAGE_ALARMRECORD(3, "报警录像存储点类"),
    /**
     *
     */
    EM_STORAGE_CARDRECORD(4, "卡号录像存储点类"),
    /**
     *
     */
    EM_STORAGE_EVENTRECORD(5, "其他事件录像存储点类"),
    /**
     *
     */
    EM_STORAGE_TIMINGSNAPSHOT(6, "定时抓图存储点类"),
    /**
     *
     */
    EM_STORAGE_MANUALSNAPSHOT(7, "手动抓图存储点类"),
    /**
     *
     */
    EM_STORAGE_VIDEODETECTSNAPSHOT(8, "视频检测抓图存储点类"),
    /**
     *
     */
    EM_STORAGE_ALARMSNAPSHOT(9, "报警抓图存储点类"),
    /**
     *
     */
    EM_STORAGE_CARDSNAPSHOT(10, "卡号抓图存储点类"),
    /**
     *
     */
    EM_STORAGE_EVENTSNAPSHOT(11, "其他事件抓图存储点类"),
    /**
     *
     */
    EM_STORAGE_TIMINGEXTRA1RECORD(12, "辅码流1录像存储点类");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_STORAGEPOINT_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_STORAGEPOINT_TYPE enumType : EM_STORAGEPOINT_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_STORAGEPOINT_TYPE enumType : EM_STORAGEPOINT_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}


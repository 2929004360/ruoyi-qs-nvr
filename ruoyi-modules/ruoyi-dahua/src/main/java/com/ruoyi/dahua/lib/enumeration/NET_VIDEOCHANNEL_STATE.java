package com.ruoyi.dahua.lib.enumeration;

/**
 * 解码通道状态
*/
public enum NET_VIDEOCHANNEL_STATE
{
    /**
     * 未知状态
    */
    NET_VIDEOCHANNEL_STATE_UNKNOWN(0, "未知状态"),
    /**
     * 空闲
    */
    NET_VIDEOCHANNEL_STATE_IDLE(1, "空闲"),
    /**
     * 播放
    */
    NET_VIDEOCHANNEL_STATE_PLAY(2, "播放"),
    /**
     * 预览
    */
    NET_VIDEOCHANNEL_STATE_MONITOR(3, "预览"),
    /**
     * 轮询
    */
    NET_VIDEOCHANNEL_STATE_TOUR(4, "轮询");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    NET_VIDEOCHANNEL_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (NET_VIDEOCHANNEL_STATE enumType : NET_VIDEOCHANNEL_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NET_VIDEOCHANNEL_STATE enumType : NET_VIDEOCHANNEL_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}


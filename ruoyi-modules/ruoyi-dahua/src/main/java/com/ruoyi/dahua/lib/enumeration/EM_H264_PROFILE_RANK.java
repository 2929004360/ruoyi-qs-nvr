package com.ruoyi.dahua.lib.enumeration;

/**
 * H264 编码级别
 *
 * @author 47040
 * @since Created at 2021/5/26 9:04
 */
public enum EM_H264_PROFILE_RANK {
    /**
     * 提供I/P帧,仅支持 progressive(逐行扫描)和 CAVLC
     */
    NET_PROFILE_BASELINE(1, "BASELINE"),
    /**
     * 提供I/P/B帧,支持 progressive 和 interlaced,提供 CAVLC 或 CABAC
     */
    NET_PROFILE_MAIN(2, "MAIN"),
    /**
     * 提供I/P/B/SP/SI帧,仅支持 progressive (逐行扫描)和 CAVLC
     */
    NET_PROFILE_EXTENDED(3, "EXTENDED"),
    /**
     * 即 FRExt,Main_Profile基础上新增：8x8 intra prediction(8x8 帧内预测), custom
     * quaint(自定义量化), lossless video coding(无损视频编码), 更多的 yuv 格式
     */
    NET_PROFILE_HIGH(4, "HIGH");

    private final int value;
    private final String note;

    EM_H264_PROFILE_RANK(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_H264_PROFILE_RANK enumType : EM_H264_PROFILE_RANK.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_H264_PROFILE_RANK enumType : EM_H264_PROFILE_RANK.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_H264_PROFILE_RANK getEnum(int value) {
        for (EM_H264_PROFILE_RANK e : EM_H264_PROFILE_RANK.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_H264_PROFILE_RANK.NET_PROFILE_BASELINE;
    }
}


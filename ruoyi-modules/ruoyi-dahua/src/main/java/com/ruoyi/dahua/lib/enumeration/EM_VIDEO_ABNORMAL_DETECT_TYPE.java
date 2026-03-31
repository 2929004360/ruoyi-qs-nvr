package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description 屏保状态
* @date 2022/03/29 13:59:54
*/
public enum EM_VIDEO_ABNORMAL_DETECT_TYPE {
/**
未知状态
*/
    EM_VIDEO_ABNORMAL_DETECT_UNKNOWN(0, "未知状态"),
/**
视频丢失
*/
    EM_VIDEO_ABNORMAL_DETECT_LOSS(1, "视频丢失"),
/**
视频遮挡
*/
    EM_VIDEO_ABNORMAL_DETECT_COVER(2, "视频遮挡"),
/**
画面冻结
*/
    EM_VIDEO_ABNORMAL_DETECT_FROZEN(3, "画面冻结"),
/**
过亮
*/
    EM_VIDEO_ABNORMAL_DETECT_LIGHT(4, "过亮"),
/**
过暗
*/
    EM_VIDEO_ABNORMAL_DETECT_DARK(5, "过暗"),
/**
场景变化
*/
    EM_VIDEO_ABNORMAL_DETECT_SCENECHANGE(6, "场景变化"),
/**
条纹检测
*/
    EM_VIDEO_ABNORMAL_DETECT_STRIATION(7, "条纹检测"),
/**
噪声检测
*/
    EM_VIDEO_ABNORMAL_DETECT_NOISE(8, "噪声检测"),
/**
偏色检测
*/
    EM_VIDEO_ABNORMAL_DETECT_UNBALANCE(9, "偏色检测"),
/**
视频模糊检测
*/
    EM_VIDEO_ABNORMAL_DETECT_BLUR(10, "视频模糊检测"),
/**
对比度异常检测
*/
    EM_VIDEO_ABNORMAL_DETECT_CONTRAST(11, "对比度异常检测"),
/**
视频运动
*/
    EM_VIDEO_ABNORMAL_DETECT_MOTION(12, "视频运动"),
/**
视频闪烁
*/
    EM_VIDEO_ABNORMAL_DETECT_FILCKERING(13, "视频闪烁"),
/**
视频颜色-13
*/
    EM_VIDEO_ABNORMAL_DETECT_COLOR(14, "视频颜色-13"),
/**
虚焦检测
*/
    EM_VIDEO_ABNORMAL_DETECT_UNFOCUS(15, "虚焦检测"),
/**
过曝检测
*/
    EM_VIDEO_ABNORMAL_DETECT_OVEREXPOSURE(16, "过曝检测"),
/**
场景剧变
*/
    EM_VIDEO_ABNORMAL_DETECT_DRAMATICCHANGE(17, "场景剧变"),
/**
黑白图像
*/
    EM_VIDEO_ABNORMAL_DETECT_BLACKWHITE(18, "黑白图像"),
/**
雪花噪声
*/
    EM_VIDEO_ABNORMAL_DETECT_SNOWNOISE(19, "雪花噪声");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_VIDEO_ABNORMAL_DETECT_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_VIDEO_ABNORMAL_DETECT_TYPE enumType : EM_VIDEO_ABNORMAL_DETECT_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_VIDEO_ABNORMAL_DETECT_TYPE enumType : EM_VIDEO_ABNORMAL_DETECT_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_VIDEO_ABNORMAL_DETECT_TYPE getEnum(int value) {
        for (EM_VIDEO_ABNORMAL_DETECT_TYPE e : EM_VIDEO_ABNORMAL_DETECT_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_VIDEO_ABNORMAL_DETECT_TYPE.EM_VIDEO_ABNORMAL_DETECT_UNKNOWN;
    }
}


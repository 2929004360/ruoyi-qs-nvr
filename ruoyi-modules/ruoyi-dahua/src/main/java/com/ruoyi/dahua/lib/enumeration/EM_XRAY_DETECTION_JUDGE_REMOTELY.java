package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  是否使用远程判图 
* @origin autoTool
* @date 2023/04/19 15:38:12
*/
public enum EM_XRAY_DETECTION_JUDGE_REMOTELY {
/**
未知, 当表示为未知时意为本地不送远程判图，集中判图时送到远程判图
*/
    EM_XRAY_DETECTION_JUDGE_REMOTELY_UNKNOWN(-1, "未知, 当表示为未知时意为本地不送远程判图，集中判图时送到远程判图"),
/**
不送远端三方进行判图
*/
    EM_XRAY_DETECTION_JUDGE_REMOTELY_NOT_SEND_JUDGE(0, "不送远端三方进行判图"),
/**
送远端三方进行判图
*/
    EM_XRAY_DETECTION_JUDGE_REMOTELY_SEND_JUDGE(1, "送远端三方进行判图");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_XRAY_DETECTION_JUDGE_REMOTELY(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_XRAY_DETECTION_JUDGE_REMOTELY enumType : EM_XRAY_DETECTION_JUDGE_REMOTELY.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_XRAY_DETECTION_JUDGE_REMOTELY enumType : EM_XRAY_DETECTION_JUDGE_REMOTELY.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_XRAY_DETECTION_JUDGE_REMOTELY getEnum(int value) {
        for (EM_XRAY_DETECTION_JUDGE_REMOTELY e : EM_XRAY_DETECTION_JUDGE_REMOTELY.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_XRAY_DETECTION_JUDGE_REMOTELY.EM_XRAY_DETECTION_JUDGE_REMOTELY_UNKNOWN;
    }
}


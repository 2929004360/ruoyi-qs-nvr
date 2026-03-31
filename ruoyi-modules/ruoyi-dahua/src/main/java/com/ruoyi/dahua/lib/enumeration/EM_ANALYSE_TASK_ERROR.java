package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  智能分析任务错误码 
* @date 2022/12/01 20:05:32
*/
public enum EM_ANALYSE_TASK_ERROR {
/**
未知
*/
    EM_ANALYSE_TASK_ERROR_UNKNOWN(1, "未知"),
/**
解码能力不足
*/
    EM_ANALYSE_TASK_ERROR_INSUFFICIENT_DECODING_CAPABILITY(2, "解码能力不足"),
/**
智能能力不足
*/
    EM_ANALYSE_TASK_ERROR_INSUFFICIENT_INTELLIGENCE_CAPABILITY(3, "智能能力不足"),
/**
码流格式不支持
*/
    EM_ANALYSE_TASK_ERROR_BITSTREAM_FORMAT_NOT_SUPPORTED(4, "码流格式不支持"),
/**
分析器离线
*/
    EM_ANALYSE_TASK_ERROR_ANALYZER_OFF_LINE(5, "分析器离线"),
/**
分析器上线
*/
    EM_ANALYSE_TASK_ERROR_ANALYZER_ON_LINE(6, "分析器上线");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_ANALYSE_TASK_ERROR(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_ANALYSE_TASK_ERROR enumType : EM_ANALYSE_TASK_ERROR.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_ANALYSE_TASK_ERROR enumType : EM_ANALYSE_TASK_ERROR.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return 0;
    }

public static EM_ANALYSE_TASK_ERROR getEnum(int value) {
        for (EM_ANALYSE_TASK_ERROR e : EM_ANALYSE_TASK_ERROR.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_ANALYSE_TASK_ERROR.EM_ANALYSE_TASK_ERROR_UNKNOWN;
    }
}


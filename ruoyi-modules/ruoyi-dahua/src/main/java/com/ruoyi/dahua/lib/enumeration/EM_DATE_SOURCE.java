package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  数据来源 
* @origin autoTool
* @date 2023/09/06 15:55:03
*/
public enum EM_DATE_SOURCE {
	/**
     * GPS
     */
    EM_DATE_SOURCE_GPS(0, "GPS"),
	/**
     * 惯性导航数据
     */
    EM_DATE_SOURCE_INERTIALNAVIGATION(1, "惯性导航数据");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_DATE_SOURCE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_DATE_SOURCE enumType : EM_DATE_SOURCE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_DATE_SOURCE enumType : EM_DATE_SOURCE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_DATE_SOURCE getEnum(int value) {
        for (EM_DATE_SOURCE e : EM_DATE_SOURCE.values()) {
            if (e.getValue() == value){
                return e;
            }
        }
        return EM_DATE_SOURCE.EM_DATE_SOURCE_GPS;
    }
}


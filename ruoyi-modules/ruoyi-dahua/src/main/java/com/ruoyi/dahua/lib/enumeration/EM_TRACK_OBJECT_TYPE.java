package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  目标类型 
* @origin autoTool
* @date 2023/09/22 13:56:24
*/
public enum EM_TRACK_OBJECT_TYPE {
	/**
     * 未知
     */
    EM_TRACK_OBJECT_TYPE_UNKNOWN(-1, "未知"),
	/**
     * 目标
     */
    EM_TRACK_OBJECT_TYPE_FACE(0, "目标"),
	/**
     * 人体
     */
    EM_TRACK_OBJECT_TYPE_HUMAN(1, "人体"),
	/**
     * 机动车
     */
    EM_TRACK_OBJECT_TYPE_VECHILE(2, "机动车"),
	/**
     * 非机动车
     */
    EM_TRACK_OBJECT_TYPE_NOMOTOR(3, "非机动车");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_TRACK_OBJECT_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_TRACK_OBJECT_TYPE enumType : EM_TRACK_OBJECT_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_TRACK_OBJECT_TYPE enumType : EM_TRACK_OBJECT_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_TRACK_OBJECT_TYPE getEnum(int value) {
        for (EM_TRACK_OBJECT_TYPE e : EM_TRACK_OBJECT_TYPE.values()) {
            if (e.getValue() == value){
                return e;
            }
        }
        return EM_TRACK_OBJECT_TYPE.EM_TRACK_OBJECT_TYPE_UNKNOWN;
    }
}


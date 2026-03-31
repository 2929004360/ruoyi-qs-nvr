package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  X光机关联图片类型 
* @origin autoTool
* @date 2023/04/19 15:40:38
*/
public enum EM_XRAY_RELATED_IMAGE_TYPE {
/**
未知
*/
    EM_XRAY_RELATED_IMAGE_TYPE_UNKNOWN(-1, "未知"),
/**
表示目标大图
*/
    EM_XRAY_RELATED_IMAGE_TYPE_LARGE_FACE(0, "表示目标大图"),
/**
表示目标小图
*/
    EM_XRAY_RELATED_IMAGE_TYPE_SMALL_FACE(1, "表示目标小图"),
/**
表示包裹可见光图
*/
    EM_XRAY_RELATED_IMAGE_TYPE_PACKAGE_VISIBLE_LIGHT(2, "表示包裹可见光图");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_XRAY_RELATED_IMAGE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_XRAY_RELATED_IMAGE_TYPE enumType : EM_XRAY_RELATED_IMAGE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_XRAY_RELATED_IMAGE_TYPE enumType : EM_XRAY_RELATED_IMAGE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_XRAY_RELATED_IMAGE_TYPE getEnum(int value) {
        for (EM_XRAY_RELATED_IMAGE_TYPE e : EM_XRAY_RELATED_IMAGE_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_XRAY_RELATED_IMAGE_TYPE.EM_XRAY_RELATED_IMAGE_TYPE_UNKNOWN;
    }
}


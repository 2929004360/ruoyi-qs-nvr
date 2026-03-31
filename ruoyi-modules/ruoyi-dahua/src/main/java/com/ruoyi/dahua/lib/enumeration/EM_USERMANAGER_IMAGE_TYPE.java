package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  图片类型 
* @origin autoTool
* @date 2023/07/28 14:28:56
*/
public enum EM_USERMANAGER_IMAGE_TYPE {
	/**
     * 未知
     */
    EM_USERMANAGER_IMAGE_TYPE_UNKNOWN(-1, "未知"),
	/**
     * 本地目标库图
     */
    EM_USERMANAGER_IMAGE_TYPE_LOCAL(0, "本地目标库图"),
	/**
     * 拍摄场景图
     */
    EM_USERMANAGER_IMAGE_TYPE_SCENE(1, "拍摄场景图"),
	/**
     * 目标抠图
     */
    EM_USERMANAGER_IMAGE_TYPE_FACE(2, "目标抠图"),
	/**
     * 红外抓图
     */
    EM_USERMANAGER_IMAGE_TYPE_INFRARED(3, "红外抓图"),
	/**
     * 陪同人员抓图
     */
    EM_USERMANAGER_IMAGE_TYPE_ENTOURAGE(4, "陪同人员抓图"),
	/**
     * 热图
     */
    EM_USERMANAGER_IMAGE_TYPE_THERMOGRAM(5, "热图");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_USERMANAGER_IMAGE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_USERMANAGER_IMAGE_TYPE enumType : EM_USERMANAGER_IMAGE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_USERMANAGER_IMAGE_TYPE enumType : EM_USERMANAGER_IMAGE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_USERMANAGER_IMAGE_TYPE getEnum(int value) {
        for (EM_USERMANAGER_IMAGE_TYPE e : EM_USERMANAGER_IMAGE_TYPE.values()) {
            if (e.getValue() == value){
                return e;
            }
        }
        return EM_USERMANAGER_IMAGE_TYPE.EM_USERMANAGER_IMAGE_TYPE_UNKNOWN;
    }
}


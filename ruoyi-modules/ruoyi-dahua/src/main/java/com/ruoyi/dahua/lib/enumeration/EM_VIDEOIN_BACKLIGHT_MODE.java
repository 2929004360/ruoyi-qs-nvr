package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  背光模式 
* @origin autoTool
* @date 2023/11/30 16:22:26
*/
public enum EM_VIDEOIN_BACKLIGHT_MODE {
	/**
     * 未知模式
     */
    EM_BACKLIGHT_UNKNOW(0, "未知模式"),
	/**
     * 关闭
     */
    EM_BACKLIGHT_OFF(1, "关闭"),
	/**
     * 背光补偿
     */
    EM_BACKLIGHT_BACKLIGHT(2, "背光补偿"),
	/**
     * 强光抑制
     */
    EM_BACKLIGHT_GLAREINHIBITION(3, "强光抑制"),
	/**
     * 宽动态
     */
    EM_BACKLIGHT_WIDEDYNAMIC(4, "宽动态"),
	/**
     * 场景自适应
     */
    EM_BACKLIGHT_SSA(5, "场景自适应");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_VIDEOIN_BACKLIGHT_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_VIDEOIN_BACKLIGHT_MODE enumType : EM_VIDEOIN_BACKLIGHT_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_VIDEOIN_BACKLIGHT_MODE enumType : EM_VIDEOIN_BACKLIGHT_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_VIDEOIN_BACKLIGHT_MODE getEnum(int value) {
        for (EM_VIDEOIN_BACKLIGHT_MODE e : EM_VIDEOIN_BACKLIGHT_MODE.values()) {
            if (e.getValue() == value){
                return e;
            }
        }
        return EM_VIDEOIN_BACKLIGHT_MODE.EM_BACKLIGHT_UNKNOW;
    }
}


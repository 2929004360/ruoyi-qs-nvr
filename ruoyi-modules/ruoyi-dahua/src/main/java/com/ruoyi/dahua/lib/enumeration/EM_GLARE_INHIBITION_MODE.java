package com.ruoyi.dahua.lib.enumeration;

/**
 * 强光抑制模式，当背光模式为GlareInhibition时有效
*/
public enum EM_GLARE_INHIBITION_MODE
{
    /**
     * 未知模式
    */
    EM_GLARE_INHIBITION_UNKONW(0, "未知模式"),
    /**
     * 默认模式
    */
    EM_GLARE_INHIBITION_DEFAULT(1, "默认模式"),
    /**
     * Fpga强光抑制模式
    */
    EM_GLARE_INHIBITION_FPGA(2, "Fpga强光抑制模式");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_GLARE_INHIBITION_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_GLARE_INHIBITION_MODE enumType : EM_GLARE_INHIBITION_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_GLARE_INHIBITION_MODE enumType : EM_GLARE_INHIBITION_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}


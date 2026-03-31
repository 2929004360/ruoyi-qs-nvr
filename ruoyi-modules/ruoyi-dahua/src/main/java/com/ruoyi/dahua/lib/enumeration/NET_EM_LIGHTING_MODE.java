package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 421657
 * @description 灯光模式
 * @origin autoTool
 * @date 2023/10/13 08:58:46
 */
public enum NET_EM_LIGHTING_MODE {
    /**
     * 未知模式
     */
    NET_EM_LIGHTING_UNKNOW(0, "未知模式"),
    /**
     * 手动模式
     */
    NET_EM_LIGHTING_MANUAL(1, "手动模式"),
    /**
     * 自动模式
     */
    NET_EM_LIGHTING_AUTO(2, "自动模式"),
    /**
     * 关闭模式
     */
    NET_EM_LIGHTING_OFF(3, "关闭模式"),
    /**
     * 倍率优先模式
     */
    NET_EM_LIGHTING_ZOOMPRIO(4, "倍率优先模式"),
    /**
     * 定时模式，本模式时LightingSchedule配置生效
     */
    NET_EM_LIGHTING_TIMING(5, "定时模式，本模式时LightingSchedule配置生效"),
    /**
     * 智能灯光模式(仅球机使用),如果在模式选择本模式，使用新加的SmartLighting配置
     */
    NET_EM_LIGHTING_SMARTLIGHT(6, "智能灯光模式(仅球机使用),如果在模式选择本模式，使用新加的SmartLighting配置"),
    /**
     * 支持多种灯光，但手动模式下只使用一种灯光，此时使用ManualLighting配置
     */
    NET_EM_LIGHTING_EXCLUSIVEMANUAL(7, "支持多种灯光，但手动模式下只使用一种灯光，此时使用ManualLighting配置"),
    /**
     * 强制打开灯光(需求要保持状态)
     */
    NET_EM_LIGHTING_FORCEON(8, "强制打开灯光(需求要保持状态)");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    NET_EM_LIGHTING_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (NET_EM_LIGHTING_MODE enumType : NET_EM_LIGHTING_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NET_EM_LIGHTING_MODE enumType : NET_EM_LIGHTING_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static NET_EM_LIGHTING_MODE getEnum(int value) {
        for (NET_EM_LIGHTING_MODE e : NET_EM_LIGHTING_MODE.values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return NET_EM_LIGHTING_MODE.NET_EM_LIGHTING_UNKNOW;
    }
}


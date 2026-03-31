package com.ruoyi.dahua.lib.enumeration;

/**
 * 非机动车颜色类型
*/
public enum EM_NONMOTOR_COLOR
{
    /**
     * 未知
    */
    EM_NONMOTOR_UNKNOWN(0, "未知"),
    /**
     * 无法识别
    */
    EM_NONMOTOR_COLOR_OTHER(1, "无法识别"),
    /**
     * 白色
    */
    EM_NONMOTOR_COLOR_WHITE(2, "白色"),
    /**
     * 橙色
    */
    EM_NONMOTOR_COLOR_ORANGE(3, "橙色"),
    /**
     * 粉色
    */
    EM_NONMOTOR_COLOR_PINK(4, "粉色"),
    /**
     * 黑色
    */
    EM_NONMOTOR_COLOR_BLACK(5, "黑色"),
    /**
     * 红色
    */
    EM_NONMOTOR_COLOR_RED(6, "红色"),
    /**
     * 黄色
    */
    EM_NONMOTOR_COLOR_YELLOW(7, "黄色"),
    /**
     * 灰色
    */
    EM_NONMOTOR_COLOR_GRAY(8, "灰色"),
    /**
     * 蓝色
    */
    EM_NONMOTOR_COLOR_BLUE(9, "蓝色"),
    /**
     * 绿色
    */
    EM_NONMOTOR_COLOR_GREEN(10, "绿色"),
    /**
     * 紫色
    */
    EM_NONMOTOR_COLOR_PURPLE(11, "紫色"),
    /**
     * 棕色
    */
    EM_NONMOTOR_COLOR_BROWN(12, "棕色"),
    /**
     * 银色
    */
    EM_NONMOTOR_COLOR_SLIVER(13, "银色"),
    /**
     * 暗紫罗兰色
    */
    EM_NONMOTOR_COLOR_DARKVIOLET(14, "暗紫罗兰色"),
    /**
     * 栗色
    */
    EM_NONMOTOR_COLOR_MAROON(15, "栗色"),
    /**
     * 暗灰色
    */
    EM_NONMOTOR_COLOR_DIMGRAY(16, "暗灰色"),
    /**
     * 白烟色
    */
    EM_NONMOTOR_COLOR_WHITESMOKE(17, "白烟色"),
    /**
     * 深橙色
    */
    EM_NONMOTOR_COLOR_DARKORANGE(18, "深橙色"),
    /**
     * 浅玫瑰色
    */
    EM_NONMOTOR_COLOR_MISTYROSE(19, "浅玫瑰色"),
    /**
     * 番茄红色
    */
    EM_NONMOTOR_COLOR_TOMATO(20, "番茄红色"),
    /**
     * 橄榄色
    */
    EM_NONMOTOR_COLOR_OLIVE(21, "橄榄色"),
    /**
     * 金色
    */
    EM_NONMOTOR_COLOR_GOLD(22, "金色"),
    /**
     * 暗橄榄绿色
    */
    EM_NONMOTOR_COLOR_DARKOLIVEGREEN(23, "暗橄榄绿色"),
    /**
     * 黄绿色
    */
    EM_NONMOTOR_COLOR_CHARTREUSE(24, "黄绿色"),
    /**
     * 绿黄色
    */
    EM_NONMOTOR_COLOR_GREENYELLOW(25, "绿黄色"),
    /**
     * 森林绿
    */
    EM_NONMOTOR_COLOR_FORESTGREEN(26, "森林绿"),
    /**
     * 海洋绿
    */
    EM_NONMOTOR_COLOR_SEAGREEN(27, "海洋绿"),
    /**
     * 深天蓝
    */
    EM_NONMOTOR_COLOR_DEEPSKYBLUE(28, "深天蓝"),
    /**
     * 青色
    */
    EM_NONMOTOR_COLOR_CYAN(29, "青色");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_NONMOTOR_COLOR(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_NONMOTOR_COLOR enumType : EM_NONMOTOR_COLOR.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_NONMOTOR_COLOR enumType : EM_NONMOTOR_COLOR.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}


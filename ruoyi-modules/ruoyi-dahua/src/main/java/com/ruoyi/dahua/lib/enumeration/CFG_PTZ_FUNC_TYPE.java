package com.ruoyi.dahua.lib.enumeration;

/**
 * @创建人 291189
 * @创建时间 2021/5/28
 * @描述
 */
public enum CFG_PTZ_FUNC_TYPE {
    /**
     * 巡航
     */
    FUNC_TYPE_TOUR(0, "巡航"),
    /**
     * 自动巡迹
     */
    FUNC_TYPE_PATTERN(1, "自动巡迹"),
    /**
     * 预置点
     */
    FUNC_TYPE_PRE(2, "预置点"),
    FUNC_TYPE_SCAN(3, "自动线扫"),
    FUNC_TYPE_MAX(4, "最大值");

   private int id;
    private String desc;

    CFG_PTZ_FUNC_TYPE(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDescById(int id) {
        for (CFG_PTZ_FUNC_TYPE enumType : CFG_PTZ_FUNC_TYPE.values()) {
            if (id == enumType.getId()) {
                return enumType.desc;
            }
        }
        return null;
    }

    public static int getIdByValue(String desc) {
        for (CFG_PTZ_FUNC_TYPE enumType : CFG_PTZ_FUNC_TYPE.values()) {
            if (desc.equals(enumType.getDesc())) {
                return enumType.getId();
            }
        }
        return -1;
    }
}


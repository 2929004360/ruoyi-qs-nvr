package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 291189
 * @version 1.0
 * @description
 * @date 2021/8/20 11:48
 */
public enum EM_TEST_ITEMS {
    EM_TEST_ITEMS_UNKNOWN(-1, "未知"),// 未知
    EM_TEST_ITEMS_OTHER(0, "其他项目"),// 其他项目
    EM_TEST_ITEMS_HAND_TEST(1, "手部测试静电电阻"),// 手部测试静电电阻
    EM_TEST_ITEMS_TWOFEET_TEST(2, "双脚测试静电电阻"),// 双脚测试静电电阻
    EM_TEST_ITEMS_HANDTWOFEET_TEST(3, "手部和双脚测试静电电阻"),// 手部和双脚测试静电电阻
    EM_TEST_ITEMS_NONE(4, "全部不测");// 全部不测

    private final int value;
    private final String note;

    EM_TEST_ITEMS(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_TEST_ITEMS enumType : EM_TEST_ITEMS.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_TEST_ITEMS enumType : EM_TEST_ITEMS.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_TEST_ITEMS getEnum(int value) {
        for (EM_TEST_ITEMS e : EM_TEST_ITEMS.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_TEST_ITEMS.EM_TEST_ITEMS_UNKNOWN;
    }
}

